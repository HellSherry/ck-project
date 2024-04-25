<script setup>

import AnswerUI from "@/components/ui/AnswerUI.vue";
import {ref} from "vue";
import ToHtml from "@/components/answer-correlation/ToHtml.vue";
const examQuestion = ref([
  {
    "ttime": "1697090842.3890967",
    "question": "<img src=\"https://cdn.jsdelivr.net/gh/HellSherry/myver1@master/2/1697177092.6081295题干.png\"><img src=\"https://cdn.jsdelivr.net/gh/HellSherry/myver1@master/2/1697177092.6081295题干.png\"><img src=\"https://cdn.jsdelivr.net/gh/HellSherry/myver1@master/2/1697177092.6081295题干.png\">",
    "tname": "Java开发之框架基础技术",
    "pname": "使用DOM4J解析XML文档",
    "answer1": "<img src=\"https://cdn.jsdelivr.net/gh/HellSherry/myver1@master/2/1697177092.6081295题干.png\"><img src=\"https://cdn.jsdelivr.net/gh/HellSherry/myver1@master/2/1697177092.6081295题干.png\"><img src=\"https://cdn.jsdelivr.net/gh/HellSherry/myver1@master/2/1697177092.6081295A.png\">",
    "answer2": "<img src=\"https://cdn.jsdelivr.net/gh/HellSherry/myver1@master/2/1697177092.6081295B.png\">",
    "answer3": "<img src=\"https://cdn.jsdelivr.net/gh/HellSherry/myver1@master/2/1697177092.6081295C.png\">",
    "answer4": "<img src=\"https://cdn.jsdelivr.net/gh/HellSherry/myver1@master/2/1697177092.6081295D.png\">",
    "answer5": "",
    "answer6": "",
    "ranswer": "3",
    "parse": "<p>使用DOM4J解析XML文档本题目考查使用DOM4J解析XML文档，其解析步骤比较固定，需要记</p><p>忆：获取SAXReader对象、读取需要解析的xML文档、开始解析。其</p><p>中的方法也比较固定，如获取根节点元素的方法为getRootElement</p><p>），因此正确答案为C</p>"
  },
  {
    "ttime": "1697090855.353295",
    "question": "<p>在JAVA中，XML说法错误的是（ ）。</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "XML概述及文档结构",
    "answer1": "<p>名称中可以包含字母、数字或其它符号</p>",
    "answer2": "<p>名称不能以数字或标点符号开头</p>",
    "answer3": "<p>标签之间要正确嵌套</p>",
    "answer4": "<p>名称中可以包含空格</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "4",
    "parse": "<p>XML概述及文档结构xml名称不能包含空格，所以选择d</p>"
  },
  {
    "ttime": "1697090864.5229042",
    "question": "<p>Java中面向对象的设计原则有（  ）。</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "XML概述及文档结构",
    "answer1": "<p>单一职责原则</p>",
    "answer2": "<p>开闭原则</p>",
    "answer3": "<p>里式替换原则</p>",
    "answer4": "<p>依赖倒置原则</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "1,2,3,4",
    "parse": "<p>XML概述及文档结构软件设计模式的原则包括：1.单一职责原则；2.开闭原则；3.里</p><p>民替换原则；4.依赖倒置原则；5.接口隔离原则；6.迪米特法则</p><p>7.合成复用原则。</p>"
  },
  {
    "ttime": "1697090876.4013908",
    "question": "<p>格式良好的XML文档需要遵循的规则，以下描述准确的是（  ）</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "XML概述及文档结构",
    "answer1": "<p>有良好的声明语句</p>",
    "answer2": "<p>有且仅有一个根元素</p>",
    "answer3": "<p>标签大小写敏感，元素正确嵌套</p>",
    "answer4": "<p>属性值用双引号包裹，标签成对/空标签关闭</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "1,2,3,4",
    "parse": "<p>XML概述及文档结构本题考的是XML文档规则1.XML文档必须包含在一个单一元素中</p><p>2.XML元素不能重鑫，必须正确的嵌套3.不能省去任何结束标</p><p>记，或者说标记必须成对4.XML元素是区分大小写的，也就是大小</p><p>值必须用引号括起，可以使用单引号，也可以使用双引号，但要始</p><p>终保持一致6．如果文档包含XML声明，则它一定是第一个出现的</p><p>东西7.XML注释和HTML注释的形式及用法相同8.名称空间的存</p><p>在是为了解决同一份文档中的同名不同义的命名冲突问题（和c++的r</p><p>amespace一样）</p>"
  },
  {
    "ttime": "1697090894.5378783",
    "question": "<p>下面说法中关于XML的描述正确的是（ ）。</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "XML概述及文档结构",
    "answer1": "<p>XML是Extensible Markup Language的缩写，即可扩展标记语言</p>",
    "answer2": "<p>XML对操作系统、编程语言都有要求</p>",
    "answer3": "<p>XML技术引用广泛、最基本的入网站、引用程序的配置一般都采用XML文件描述</p>",
    "answer4": "<p>XML与HTML性质是一样的</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "1,3",
    "parse": "<p>XML概述及文档结构&amp;ML与操作系统、编程语言的开发平台都无关；与HTML性质不一样，</p><p>HTML是超文本标记语言，XML不是HTML的替代。XML和HTMI</p><p>为不同的目的而设计：XMLL被设计用来传输和存储数据，其焦点是</p><p>数据的内容。HTML被设计用来显示数据，其焦点是数据的外观。</p><p>HTML旨在显示信息，而XML旨在传输信息。</p>"
  },
  {
    "ttime": "1697090910.5287635",
    "question": "<p>下列使用XML描述图书信息正确的是（ ）。</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "XML概述及文档结构",
    "answer1": "<p>&lt;?xml version=\"1.0\" encoding= \"UTF-8\"?&gt;</p><p>&lt;book&gt;</p><p class=\"ql-indent-1\">&lt;book id =\"bk102\"&gt;</p><p class=\"ql-indent-1\">&lt;title&gt;Java开发之框架基础技术&lt;/title&gt;</p><p class=\"ql-indent-1\">&lt;author&gt;北大青鸟&lt;/author&gt;</p><p class=\"ql-indent-1\">&lt;/book&gt;</p><p>&lt;/book&gt;</p>",
    "answer2": "<p>&lt;?xml version=\"1.0\" encoding= \"UTF-8\"?&gt;</p><p>&lt;books&gt;</p><p class=\"ql-indent-1\">&lt;book&nbsp;id&nbsp;=&nbsp;\"bk101\"&gt;</p><p class=\"ql-indent-1\">&lt;title&gt;Java开发之框架基础技术&lt;/title&gt;</p><p class=\"ql-indent-1\">&lt;author&gt;北大青鸟&lt;/author&gt;</p><p>&lt;/book&gt;</p><p>&lt;/books&gt;</p>",
    "answer3": "<p>&lt;?xml version=\"1.0\" encoding= \"UTF-8\"?&gt;</p><p>&lt;book&gt;</p><p class=\"ql-indent-1\">&lt;title&gt;Java开发之框架基础技术&lt;/title&gt;</p><p class=\"ql-indent-1\">&lt;author&gt;北大青鸟&lt;/author&gt;</p><p>&lt;/book&gt;</p>",
    "answer4": "<p>&lt;?xml version=\"1.0\" encoding= \"UTF-8\"?&gt;</p><p>&lt;book&gt;</p><p class=\"ql-indent-1\">&lt;title&gt;</p><p class=\"ql-indent-1\">&lt;name&gt;</p><p class=\"ql-indent-1\">Java开发之框架基础技术</p><p class=\"ql-indent-1\">&lt;/title&gt;</p><p class=\"ql-indent-1\">&lt;/name&gt;</p><p class=\"ql-indent-1\">&lt;author&gt;北大青鸟&lt;/author&gt;</p><p>&lt;/book&gt;</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "2",
    "parse": "<p>XML概述及文档结构暂无</p>"
  },
  {
    "ttime": "1697090931.9954739",
    "question": "<p>下面选项不是XML文档根元素的特点的是（  )。</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "XML概述及文档结构",
    "answer1": "<p>根元素是一个完全包括文档中其他所有元素的元素</p>",
    "answer2": "<p>根元素的起始标签要放在所有其他元素的起始标签之前</p>",
    "answer3": "<p>根元素的结束标签要放在所有其他元素的结束标签之后</p>",
    "answer4": "<p>根元素的起始标签可以不放在所有其他元素的起始标签之前</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "4",
    "parse": "<p>XML概述及文档结构暂无</p>"
  },
  {
    "ttime": "1697090941.7289352",
    "question": "<p>下列关于XML文档的说法，错误的是（）。</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "使用DOM解析和编辑XML（XML基本概念）",
    "answer1": "<p>XML与操作系统、编程语言的开发平台都有关</p>",
    "answer2": "<p>XML中不可以存在特殊字符</p>",
    "answer3": "<p>元素由开始标签、结束标签和元素内容构成</p>",
    "answer4": "<p>每个XML文档必须有且仅有一个根元素</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "1,2",
    "parse": "使用DOM解析和编辑XML（XML基本概念）答案中xML是跨平台的，与操作系统无关，b答案中是可以存在特殊字<br>符的，框架的配置文件中经常出现<br>"
  },
  {
    "ttime": "1697090952.8305702",
    "question": "<p>D0M维护XML数据的步骤中，添加信息的步骤中顺序正确的是（ ）。</p><p>（1）为XML文档构造D0M树。</p><p>（2）把节点添加到其他所属的父节点上</p><p>（3）保存XML文档</p><p>（4）创建新节点、并设置name属性。</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "使用DOM解析和编辑XML（XML基本概念）",
    "answer1": "<p>(1）(3)(2)（4)</p>",
    "answer2": "<p>（1）(4)（2)（3)</p>",
    "answer3": "<p>(4）(3)(2)(1)</p>",
    "answer4": "<p>（1）(2)（4)（3)</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "2",
    "parse": "使用DOM解析和编辑XML（XML基本概念）大致过程：创建DocumentBuilderFactory工厂对象用工厂对象创<br>建DocumentBuilder对象用DocumentBuilder对象将xml文件加载<br>进内存，用Document对象接收获取到所有person节点，返回NodeL<br>ist循环遍历NodeList里的person节点用getElementByTagName<br>）；分别获取person标签节点里的name标签节点、email标签节点<br>用getFirstNode（）.getNodeValue（）；分别获取name标签节点、em<br>ail标签节点的第一个子节点的内容<br>"
  },
  {
    "ttime": "1697090972.8206866",
    "question": "<p>下列有关XML文档的说法错误的是（），</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "XML概述及文档结构",
    "answer1": "<p>XML文档允许用户自定义标签</p>",
    "answer2": "<p>XML文档可用于数据存储、数据交换等场合</p>",
    "answer3": "<p>XML文档中所有标签不区分大小写</p>",
    "answer4": "<p>XML文档总是以一个XML声明开始</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "3",
    "parse": "<p>XML概述及文档结构XML文档标签名区分大小写</p>"
  },
  {
    "ttime": "1697090982.2114167",
    "question": "<p>使用DOM在XML文档中添加一个节点的步骤不包括（  )。</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "XML概述及文档结构",
    "answer1": "<p>为XML文档构造DOM树</p>",
    "answer2": "<p>创建根节点</p>",
    "answer3": "<p>创建新节点，并设置name属性</p>",
    "answer4": "<p>把节点添加到其所属父节点上</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "2",
    "parse": "<p>XML概述及文档结构使用DOM在XML文档中添加一个节点的步骤为：1.XML文档构造DOM</p><p>树；2.创建新节点，并设置name属性；3.创建新节点，并设置na</p><p>me属性；4.把节点添加到其所属父节点上；5.保存XML文档。所</p><p>以该题选B。</p>"
  },
  {
    "ttime": "1697090995.444625",
    "question": "<p>以下有关DOM模型结构的说法错误的是（ \t）</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "XML概述及文档结构",
    "answer1": "<p>元素：元素是XML的基本构建</p>",
    "answer2": "<p>属性：属性节点是元素的子节点，包含关于元素节点的信息</p>",
    "answer3": "<p>文本：确切来讲，文本节点是文本。它可以包含许多信息或仅仅是空白</p>",
    "answer4": "<p>根节点：根节点是文档中其他元素的父节点</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "2",
    "parse": "<p>XML概述及文档结构本题考查的是dom的模型结构，dom的基本概念</p>"
  },
  {
    "ttime": "1697091007.821429",
    "question": "<p>以下关于XML的说法错误的是（\t\t）。</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "XML概述及文档结构",
    "answer1": "<p>XML又称为可扩展标记语言</p>",
    "answer2": "<p>XML与操作系统、编程语言的开发平台无关</p>",
    "answer3": "<p>XML的作用有数据存储，数据交换，数据分析，数据配置</p>",
    "answer4": "<p>XML规范统一，实现不同系统之间的数据交换</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "3",
    "parse": "<p>XML概述及文档结构本题考察XML的基本概念，特点与作用a，b，d正确。c，错误，没有数</p><p>据分析的功能xm1的主要作用有：1、用来保存数据，而且这些数</p><p>据具有自我描述性2、它还可以作为项目或者模块的配置文件3、</p><p>还可以用来做网络传输数据的格式（现在用不多了，都是JSON为主</p>"
  },
  {
    "ttime": "1697091019.9360726",
    "question": "<p>关于解析xml文档技术描述正确的是（\t）。</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "XML概述及文档结构",
    "answer1": "<p>D0M解析基于Xml的树结构完成解析，因此Dom解析是节省资源的</p>",
    "answer2": "<p>SAX是基于事件解析，因此它的优势是占用资源少，内存消耗小</p>",
    "answer3": "<p>JDoM是针对java的特定文档模型，它简化了和xml的交互</p>",
    "answer4": "<p>DOM4J是一个优秀的Java XML API，性能优异，它并不是一个开源库</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "2,3",
    "parse": "<p>XML概述及文档结构本题考查xml解析技术的概念问题，a是消耗资源d是升源库；bc选</p><p>项是正确的</p>"
  },
  {
    "ttime": "1697091026.728474",
    "question": "<p>使用DOM解析XML时以下（\t）对象是不在范围内。</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "XML概述及文档结构",
    "answer1": "<p>Node对象</p>",
    "answer2": "<p>NodeList对象</p>",
    "answer3": "<p>Document对象</p>",
    "answer4": "<p>DocumentList对象</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "4",
    "parse": "<p>XML概述及文档结构使用DoM解析xML时我们主要使用的对象包括Node对象、NodeList对</p><p>象、Document对象、Element对象等。所以该题选D。</p>"
  },
  {
    "ttime": "1697091032.0279095",
    "question": "<p>XML中的特殊字符中，特殊字符对应的实体名错误的（\t\t）。</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "XML概述及文档结构",
    "answer1": "<p>&lt; 对应 &amp;lt；</p>",
    "answer2": "<p>&gt; 对应 &amp;gt：</p>",
    "answer3": "<p>&amp; 对应 &amp;</p>",
    "answer4": "<p>\" 对应  &amp;quot;</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "3",
    "parse": "<p>XML概述及文档结构对应&amp;</p>"
  },
  {
    "ttime": "1697091037.2541883",
    "question": "<p>以下关于dom4j解析xML的相关API，说法错误的是（</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "使用DOM4J解析XML文档",
    "answer1": "<p>常用接口有Node，Branch，Element，Document</p>",
    "answer2": "<p>通过Document接口的rootElement（）方法可以获得根节点</p>",
    "answer3": "<p>通过Element接口下的elements（）方法可以获得某个元素下所有的子元素</p>",
    "answer4": "<p>Element接口设置属性的方法是setAttribute（String name，String value)</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "2,4",
    "parse": "<p>使用DOM4J解析XML文档本题考察是dom4j的apia，正确。b，错误Document接口获取根节</p><p>点的方法是getRootElement（）。c,正确。d,错误，Element接口设</p><p>置属性的方法是addAttribute（String&nbsp;name，String&nbsp;value）涉</p><p>及的相关方法（包括对xml文档常用的增删改查方法）：Elementge</p><p>tRootElement（）：Document的方法，用来获取根元素；Listele</p><p>ments（）：Element的方法，用来获取所有子元素；Stringattri</p><p>buteValue（Stringname）：Element的方法，用来获取指定名字的</p><p>属性值；Elementelement（Stringname）：Element的万法，用</p><p>来获取第一个指定名字的子元素；ElementelementText（String</p><p>name）：Element的方法，用来获取第一个指定名字的子元素的文-</p><p>本内容。Element&nbsp;addElement（String&nbsp;name）：Element的方法</p><p>，为当前元素添加指定名字子元素。返回值为新建元素对象；setT</p><p>ext（Stringtext）：Element的方法，为当前元素设置文本内容。</p><p>addAttribute（String&nbsp;name，String&nbsp;value):Element的方法，</p><p>为当前元素添加属性。booleanremove（Elemente）：Element和</p><p>Document的方法，移除指定子元素；ElementgetParent（）：获</p><p>取父元素，根元素的父元素为null。DocumentHelper.createDoc</p><p>ument（):创建Document对象；DocumentHelper.createElement</p><p>（Stringname）：创建指定名称的Element元素。intindexOf（No</p><p>denode）：Branch的方法，查找指定节点，在当前Branch的子节点</p><p>集合中的下标位置。</p>"
  },
  {
    "ttime": "1697091054.069139",
    "question": "<p>使用DOM解析XML时使用Node对象，下列说法错误的是（ )。</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "XML概述及文档结构",
    "answer1": "<p>getChildNodes（）：返回包含此节点所有子节点的Node。</p>",
    "answer2": "<p>getNextSibling（）：返回在DOM树中这个节点的下一个兄弟节点。</p>",
    "answer3": "<p>geiPreviousSiblint(）：返回在DOM树中这个节点的上一个兄第节点。</p>",
    "answer4": "<p>getNodeName（）：返回所有节点的名称。</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "1,4",
    "parse": "<p>XML概述及文档结构暂无</p>"
  },
  {
    "ttime": "1697091060.0155466",
    "question": "<p>使用Dom4i解析xML文件部分的代码，错误的代码行是（）</p><p>SAXReader&nbsp;reader=new&nbsp;SAXReader(); //1</p><p>Document&nbsp;document=reader.parse(new&nbsp;File(\"input.xml\"))</p><p>//2</p><p>Element&nbsp;root=document.getRootElement();//3</p><p>Element element=root.element(\"root\");//4</p>",
    "tname": "Java开发之框架基础技术",
    "pname": "使用DOM4J解析XML文档",
    "answer1": "<p>1</p>",
    "answer2": "<p>2</p>",
    "answer3": "<p>3</p>",
    "answer4": "<p>4</p>",
    "answer5": "",
    "answer6": "",
    "ranswer": "2",
    "parse": "<p>使用DOM4J解析XML文档Document对象相关1.读取xML文件，获得document对象.</p><p>SAXReader&nbsp;reader&nbsp;=&nbsp;new&nbsp;SAXReader&nbsp;();</p><p>Do&nbsp;C</p><p>ument&nbsp;document&nbsp;=&nbsp;reader.read(new&nbsp;File(\"input.xml\"));2</p><p>.解析xML形式的文本，得到document对象，</p><p>Stringt</p><p>ext&nbsp;=&nbsp;\"*;</p><p>Document&nbsp;document&nbsp;=&nbsp;DocumentHelpe</p><p>r.parseText（text）：3.主动创建document对象.</p><p>D</p><p>ocument&nbsp;document&nbsp;=&nbsp;DocumentHelper.createDocument();</p><p>Element&nbsp;root&nbsp;=&nbsp;document.addElement(\"members\");</p><p>//创建根节点</p>"
  }])

const ref1 = ref("");
const value = ref(1);

const options = ref([examQuestion.value.answer1,examQuestion.value.answer2,examQuestion.value.answer3,examQuestion.value.answer4]);

</script>

<template>


  <t-space v-if="true" style="width: 100%;" direction="vertical">
  <t-card v-for="(item,index) in examQuestion" style="width: 80%;margin:20px auto;min-width: 100px;max-width: 800px" :bordered="false" hoverShadow size="small" theme="poster2">
    <div :style="item.ranswer==null||item.ranswer==''?'':'background-color: var(--td-brand-color-4);' " class="subscribe">
      <div :style="'position: absolute;left: 1px;top: 46px;width: 0;height: 0;border-right: 0px solid transparent;border-left: 9px solid transparent;border-top: 9px solid var(--td-brand-color-'+(item.ranswer==null||item.ranswer==''?'8':'5')+');'" class="sdf"></div>
     Topic {{ (index+1+'').padStart(2, "0") }}
    </div>
    <t-check-tag style="float: right;"   :checked="true" :checked-props="{
  theme: 'primary',
  variant: 'outline',
}">选择一项</t-check-tag>

    <br>
    <ToHtml style="overflow: hidden;width: 90%;margin: 0 auto;  font-weight: 600;" v-html="item.question"/>

    <AnswerUI :answerLen="4" style="overflow: hidden;width: 90%;margin: 30px auto;"  :checkbox="false"  v-model="item.ranswer" :options="[item.answer1,item.answer2,item.answer3,item.answer4]"/>
  </t-card>
  </t-space>
  <div  v-else>

    <div style="overflow: hidden">
      <t-tabs v-model="value" style="margin-top: -48px;background-color: rgba(255,255,255,0)" :default-value="1">
        <t-tab-panel style="" :value="index+1" :label="index+1" v-for="(item,index) in examQuestion" >
          <t-card style="width: 80%;margin:20px auto;min-width: 100px;max-width: 800px" :bordered="false" shadow size="small" theme="poster2">
            <div :style="item.ranswer==null||item.ranswer==''?'':'background-color: var(--td-brand-color-4);' " class="subscribe">
              <div :style="'position: absolute;left: 1px;top: 46px;width: 0;height: 0;border-right: 0px solid transparent;border-left: 9px solid transparent;border-top: 9px solid var(--td-brand-color-'+(item.ranswer==null||item.ranswer==''?'8':'5')+');'" class="sdf"></div>
              Topic {{ (index+1+'').padStart(2, "0") }}
            </div>
            <div  style="float: right;">
              <t-check-tag   :checked="true" :checked-props="{
  theme: 'primary',
  variant: 'outline',
}">选择一项</t-check-tag>
              <button :disabled="value<=1" @click="value--">上一题</button>
              <button :disabled="value>=examQuestion.length" @click="value++">下一题</button>
            </div>

            <br>
            <ToHtml style="overflow: hidden;width: 90%;margin: 0 auto;  font-weight: 600;" v-html="item.question"/>

            <AnswerUI :answerLen="4" style="overflow: hidden;width: 90%;margin: 30px auto;"  :checkbox="false"  v-model="item.ranswer" :options="[item.answer1,item.answer2,item.answer3,item.answer4]"/>
          </t-card>
        </t-tab-panel>

      </t-tabs>

    </div>

  </div>


<!--      <t-swiper-->
<!--          :duration="300"-->
<!--          :interval="2000"-->
<!--          trigger="click"-->
<!--          navigationType="dots"-->
<!--          :stopOnHover="false"-->
<!--          :loop="false"-->
<!--          :autoplay="false"-->
<!--      >-->
<!--        <t-swiper-item v-for="(item,index) in examQuestion">-->
<!--          <t-card style=" width: 80%;margin:20px auto;min-width: 100px;max-width: 800px" :bordered="false" hoverShadow size="small" theme="poster2">-->
<!--            <div :style="item.ranswer==null||item.ranswer==''?'':'background-color: var(&#45;&#45;td-brand-color-4);' " class="subscribe">-->
<!--              <div :style="'position: absolute;left: 1px;top: 46px;width: 0;height: 0;border-right: 0px solid transparent;border-left: 9px solid transparent;border-top: 9px solid var(&#45;&#45;td-brand-color-'+(item.ranswer==null||item.ranswer==''?'8':'5')+');'" class="sdf"></div>-->
<!--              Topic {{ (index+1+'').padStart(2, "0") }}-->
<!--            </div>-->
<!--            <t-check-tag style="float: right;"   :checked="true" :checked-props="{-->
<!--  theme: 'primary',-->
<!--  variant: 'outline',-->
<!--}">选择一项</t-check-tag>-->

<!--            <br>-->
<!--            <ToHtml  style="overflow: hidden; width: 90%;margin: 0 auto;  font-weight: 600;" v-html="item.question"/>-->

<!--            <AnswerUI :answerLen="4" style="overflow: hidden;width: 90%;margin: 30px auto;"  :checkbox="false"  v-model="item.ranswer" :options="[item.answer1,item.answer2,item.answer3,item.answer4]"/>-->
<!--          </t-card>-->
<!--        </t-swiper-item>-->
<!--      </t-swiper>-->
  </template>




<style scoped>


.sdf {
  position: absolute;content: "";left: 1px;top: 46px;width: 0;height: 0;border-right: 0px solid transparent;border-left: 9px solid transparent;border-top: 9px solid var(--td-brand-color-8);
}



.subscribe {
  position: relative;

  border-bottom-right-radius: 30px;
  border-top-right-radius: 30px;
  background-color: var(--td-brand-color-6);
  color: #FFF;
  padding: 12px 25px;
  display: inline-block;
  font-size: 12px;
  font-weight: bold;
  letter-spacing: 5px;
  left:-26px;
  top: -20px;
  cursor: pointer;
  transition: all .25s ease;
  box-shadow: -5px 6px 20px 0px rgba(26, 26, 26, 0.4);
}

.subscribe:hover {
  background-color: var(--td-brand-color-8);
  box-shadow: -5px 6px 20px 0px rgba(88, 88, 88, 0.569);
}
</style>