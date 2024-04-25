import { GradioChatBot } from '../src';

const bot = new GradioChatBot('6');

async function start() {
  console.log(await bot.chat('hello'));
  console.log(await bot.chat('1+1'));
  console.log(await bot.chat('再+2'));
}

start();