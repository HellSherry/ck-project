#!/usr/bin/env node

import express from 'express';
import cors from 'cors'
import assert from 'assert';
import 'express-async-errors';
import { GradioChatBot } from '../';

export type Role = 'user' | 'assistant' | 'system'
export type Action = 'next' | 'variant';

export interface APIMessage {
  role: Role
  content: string
}

export interface APIRequest {
  model: string
  action: Action
  prompt?: string | string[]
  messages: APIMessage[]
  stream?: boolean
}

export interface APIResponse {
  whisper?: string
  usage?: {
    prompt_tokens: number;
    completion_tokens: number;
    total_tokens: number;
  }
  choices: {
    text: string
    delta?: APIMessage
    message: APIMessage
    finish_reason?: string
  }[]
}

const PORT = isNaN(parseInt(process.env.PORT, 10)) ? 8000 : parseInt(process.env.PORT, 10);
const app = express();
app.use(cors());
app.use(express.json());
app.use(function (err, req, res, next) {
  res
    .status(err.status || 500)
    .send({ message: err.message, stack: err.stack });
});

function parseOpenAIMessage(request: APIRequest) {
  const history: [string, string][] = [];
  request.messages?.forEach((message) => {
    if (message.role === 'user') {
      history.push([message.content, '']);
    } else if (history.length) {
      history.at(-1)[1] = message.content;
    }
  })
  const prompts = [request.messages?.reverse().find((message) => message.role === 'user')?.content];
  if (typeof request.prompt === 'string') {
    prompts.push(request.prompt);
  } else if (Array.isArray(request.prompt)) {
    prompts.push(...request.prompt);
  }
  return {
    history,
    prompt: prompts.filter(Boolean).join('\n'),
    model: request.model,
    stream: request.stream,
  };
}

function responseOpenAIMessage(content: string, input: string, finish_reason = null): APIResponse {
  const message: APIMessage = {
    role: 'assistant',
    content,
  };
  return {
    whisper: input,
    usage: {
      prompt_tokens: input.length,
      completion_tokens: content.length,
      total_tokens: 100000000,
    },
    choices: [{
      delta: message,
      message,
      finish_reason,
      text: content,
    }],
  };
}

app.post(['/', '/api/conversation'], async (req, res) => {
  const { prompt, model, history, stream } = parseOpenAIMessage(req.body);

  const chatbot = new GradioChatBot({
    url: model,
    historySize: 20,
  });
  chatbot.history = history;
  const isStream = stream || req.headers.accept?.includes('text/event-stream');
  if (isStream) {
    res.set('Content-Type', 'text/event-stream; charset=utf-8');
  }
  assert(prompt, 'messages can\'t be empty!');
  let lastLength = 0;
  const content = await chatbot.chat(prompt, {
    onMessage(msg) {
      if (isStream) {
        res.write(`data: ${JSON.stringify(responseOpenAIMessage(msg.slice(lastLength), prompt))}\n\n`);
        lastLength = msg.length
      }
    }
  }).catch(error => {
    console.log('Error:', error)
    return error;
  });
  if (isStream) {
    res.write(`data: [DONE]\n\n`);
  } else {
    const response = responseOpenAIMessage(content, prompt, 'stop');
    res.json(response);
  }
});

app.post(/.*\/completions$/, async (req, res) => {
  const { prompt, model, history, stream } = parseOpenAIMessage(req.body);
  const chatbot = new GradioChatBot({
    url: model,
    historySize: 20,
  });
  chatbot.history = history;
  const isStream = stream || req.headers.accept?.includes('text/event-stream');
  if (isStream) {
    res.set('Content-Type', 'text/event-stream; charset=utf-8');
  }
  let lastLength = 0;
  assert(prompt, 'messages can\'t be empty!');
  const content = await chatbot.chat(prompt, {
    onMessage(msg) {
      if (isStream) {
        res.write(`data: ${JSON.stringify(responseOpenAIMessage(msg.slice(lastLength), prompt))}\n\n`);
        lastLength = msg.length;
      }
    }
  }).catch(error => {
    console.log('Error:', error)
    return error;
  });
  if (isStream) {
    res.end(`data: [DONE]\n\n`);
  } else {
    const response = responseOpenAIMessage(content, prompt, 'stop');
    res.json(response);
  }
});

app.get(['/', '/api/conversation'], async (req, res) => {
  const { text, model } = req.query || {};
  if (!text) {
    return res.status(500).write('text can\'t be empty!');
  }
  res.set('Cache-Control', 'no-cache');
  res.set('Content-Type', 'text/event-stream; charset=utf-8');
  let lastLength = 0;
  const chatbot = new GradioChatBot({
    url: String(model || '0'),
    historySize: 20,
  });
  const content = await chatbot.chat(String(text), {
    onMessage: (msg) => {
      res.write(msg.slice(lastLength));
      lastLength = msg.length;
    }
  }).catch(error => {
    console.log('Error:', error)
    return error;
  });
  res.end(content.slice(lastLength));
});

app.listen(Math.max(Math.min(65535, PORT), 80), '0.0.0.0');
console.log(`\nServer start successful, serve link: http://localhost:${PORT}/api/conversation?text=hello\n`);

/**

curl http://127.0.0.1:8000/api/completions \
  -H "accept: text/event-stream" \
  -H "Content-Type: application/json" \
  -d '{
     "model": "gpt-3.5",
     "messages": [{"role": "user", "content": "hello"}]
   }'
 */