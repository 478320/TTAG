# 天天助农



**选题背景：**

1.随着全球健康意识的提升和互联网技术的发展，人们对新鲜、健康、可追溯的农产品需求日益增长。然而，传统的农产品供应链长，中间环节多，导致消费者往往难以获得新鲜产品，同时农民的利润也被不断压缩。对此，我们看到了通过技术手段来改善这一状况的巨大潜力。

2.农民缺乏有效的销售渠道和市场推广知识，而消费者则缺乏一个可靠的平台来直接购买优质农产品。在这样的市场背景下，天天助农商城应运而生，旨在打造一个连接农民和消费者的直销平台，减少中间环节，同时提供质量保证和完善的服务体验。

 

**实现的目标：**

1.建立直连渠道：建立一个稳定可靠的电商平台，让农民可以直接将产品销售给消费者，提高农民的收入，同时保证消费者能够买到质量上乘、价格合理的农产品。

2.优化供应链：通过平台优化整个供应链流程，减少不必要的物流和储存环节，从而保证产品的新鲜度和降低成本。

3.支持可持续发展：推动环保包装和物流，支持可持续农业发展，扶持小规模和有机农业生产，符合绿色生态的发展趋势。

4.构建社区互动：建立用户社区，促进消费者与农民之间的沟通和互动，打造亲民的品牌形象。

通过实现这些目标，天天助农商城平台旨在成为农产品直销市场的领导者，为农民和消费者之间建立起一个健康、便捷的交易环境。

 

（二）功能说明

 

天天助农商城是一个专注于农产品直销的电子商务平台，旨在通过技术创新，连接农民和消费者，打造从田间到餐桌的绿色供应链。该平台通过集成现代互联网技术和大数据分析，提供一站式的农产品销售和购买解决方案，同时支持农民在农业生产过程中的信息化管理，增强其市场竞争力。

 

**功能描述：**

 

**农产品直销**：农民可以直接在平台上发布自己的农产品，消费者可以浏览并购买，减少中间环节，确保产品新鲜度和价格合理性。

**透明化信息**：将来将会完成对于农产品的报告的展示，每个产品都配有详绑的生长周期记录和品质检验报告，保证了产品信息的透明化。

**社区交流**：我们将在将来搭建农民与消费者之间的社区，可以交流农业知识，分享农业技术，增进彼此理解。

**物流追踪**：目前未真正引入物流的Api，但展示了概念，真正整合物流信息后，用户可以实时追踪订单状态，确保配送高效可靠。

//缺少主要界面

 

**主要界面**：

**首页**：显示平台特色，包括产品大厅的推荐产品，产品的评分和产品的部分信息，包含右上角的快捷操作按钮

 

![img](file:///C:/Users/余思衡/AppData/Local/Temp/msohtmlclip1/01/clip_image002.png)

 

**产品详情页**：详细介绍每个农产品的价格，图片展示，描述详情，等信息，以及购买该产品的用户的评论，以及购买选项。将来还会引入产品的质量等信息

 

![img](file:///C:/Users/余思衡/AppData/Local/Temp/msohtmlclip1/01/clip_image004.png)

 

**个人中心**：用户管理个人信息、填写个人邮箱和地址等的页面。

 

![img](file:///C:/Users/余思衡/AppData/Local/Temp/msohtmlclip1/01/clip_image006.png)

 

**订单页面**：用户可在该页面查看购买的物品的订单状态，物流信息等内容

 

**待支付订单**

![img](file:///C:/Users/余思衡/AppData/Local/Temp/msohtmlclip1/01/clip_image008.png)

**其他订单**

![img](file:///C:/Users/余思衡/AppData/Local/Temp/msohtmlclip1/01/clip_image010.png)

**物流信息**

![img](file:///C:/Users/余思衡/AppData/Local/Temp/msohtmlclip1/01/clip_image012.png)

 

**后台页面**：为管理员设计的页面，管理农户的申请和产品的上线

 

**![img](file:///C:/Users/余思衡/AppData/Local/Temp/msohtmlclip1/01/clip_image014.png)**

 

**社区论坛**：农民和消费者的互动交流平台，可以发帖、评论和分享。

（待开发敬请期待）

 

**数据分析**：专为农民设计的界面，提供生产、销售数据的可视化分析。

（待开发敬请期待）

 

通过这些功能和界面，天天助农商城平台不仅能够为消费者提供优质的农产品，也能为农民提供强大的生产和销售支持，共同推进农业的可持续发展。

 

（三）创新要点

 

**技术创新：**

 

**后端：**

1.微服务架构：采用微服务架构设计，提高系统的可扩展性和可维护性，使得每个服务模块都能独立更新，减少系统整体的下线时间，提高用户体验。

2.容器化部署：通过Docker容器化部署和Kubernetes集群管理，实现快速部署和自动化运维，提高系统的可靠性和弹性扩展能力。

3.数据加密与安全：实现先进的数据加密技术和安全策略，如使用HTTPS、JWT（Json Web Token）进行数据传输和接口访问控制，确保用户数据和交易安全。

4.分布式事务处理：通过集成Seata框架，我们解决了在微服务架构下分布式事务一致性的问题。Seata通过提供AT、TCC、SAGA和XA事务模式，确保了跨服务调用时的数据一致性，提升了系统的稳定性和用户体验。

5.动态服务发现与配置：采用Nacos实现服务的注册与发现以及动态配置管理。这不仅提高了服务配置的灵活性，降低了服务间耦合度，也使得服务的扩展和维护更加方便。Nacos作为服务配置中心，还提供了配置的版本管理，可以快速回滚到旧版本配置。

6.消息驱动架构：利用RabbitMQ实现了消息驱动的微服务架构，该架构支持异步处理和服务之间的解耦，提高了系统的响应速度和可扩展性。RabbitMQ还帮助我们在订单处理、通知发送等场景实现了高效的消息传递机制。

 

**前端**：

**1.****模块化和组件化开发**:

**Vue 3** **和 Element Plus 的结合**，为天天助农平台提供了模块化和组件化的开发方式。这不仅使得代码更加清晰、易于维护，也使得重用组件成为可能，极大地提高了开发效率和应用的一致性。

**2.****现代化的前端构建工具**:

Vite 的引入，为项目带来了极速的启动和热更新反馈，极大地缩短了开发迭代周期。与传统的构建工具相比，Vite 利用现代浏览器对 ES 模块的原生支持，省去了打包步骤，实现了即时模块热更新，大幅提升了开发体验。

**3.****状态管理的简化**:

**Pinia** 作为状态管理库，相比传统的 Vuex 有更简单的 API 和更好的 TypeScript 集成。它为天天助农平台提供了一种更为灵活、易于理解和维护的状态管理方式，尤其适合利用 Vue 3 的 Composition API 构建复杂的应用状态逻辑。

**4.****类型安全和开发体验的提升**:

**TypeScript** 的使用，为开发团队提供了静态类型检查，减少了运行时错误，并提高了代码的质量和可维护性。同时，TypeScript 的类型系统为开发者带来了更好的代码提示和自动补全功能，从而提升了开发效率。

5.**路由管理和页面导航的高级特性**:

**Vue Router** 为天天助农平台提供了灵活的路由管理能力，支持复杂的页面导航逻辑、异步路由等高级特性。这使得应用能够提供丰富的用户界面和流畅的用户体验。

6.**高效的数据交互和管理**:

**Axios** 的引入，使得平台能够方便地处理 HTTP 请求、响应拦截、取消请求等，与后端的数据交互变得更加灵活和高效。这对于需要频繁网络请求的现代 Web 应用来说至关重要。

 

**UI**：在天天助农产品的UI设计中，我采用了清新简洁的风格，突出了农产品的自然属性。界面色调以棕色为主色调，搭配自然的黄色和绿色，让用户在浏览产品时感受到清新自然的氛围。为方便用户购买，我在产品界面加入了一键下单功能，用户只需点击一次就可以直接进行订单的布置，简便实用。在产品展示界面，用户可以通过产品的详细介绍，滑轮图片介绍，以及其他购买用户给出星级以及评价来对产品进行初步了解，判断产品符合自己心意并选择是否下单。

 

**模式创新：**

 

1.创新的商业模式：提供一站式服务，除了基础的商城功能，还提供如商品智能推荐、物流追踪、支付服务等增值服务，形成完整的生态系统。

2.后台管理系统：通过后台对农户和产品的审核，不仅可以让用户获得绿色健康的农产品，还可以及时指导农户进行产品的上架，提高贫农中农等小型农户的收益 

3.社群经济：围绕产品构建社区和社群，鼓励用户生成内容（UGC），利用社群的力量进行品牌宣传和产品推荐，形成口碑营销效应。

 

**市场应用创新：**

 

1.细分市场深耕：专注于特定细分市场，如天天助农专注于农产品的直销，通过深入了解农产品市场和消费者需求，提供更符合市场需求的解决方案。

2.跨界合作：与不同行业的企业进行跨界合作，比如与物流公司合作，提供更快捷的物流服务；或者与金融服务商合作，提供更便捷的支付和金融服务。

3.持续的用户教育：将来开放的社区平台将进行持续的用户教育，提高消费者对农产品品质的认识和对农业可持续发展的理解，形成品牌忠诚度。

 

（四）系统开发工具与技术

 

**技术环境和要求**：

**编程语言**：主要使用Java进行后端开发，利用其成熟的生态和强大的社区支持。

**开发框架**：后端开发主要基于Spring Boot和Spring Cloud，利用这两个框架提供的快速开发能力和微服务支持。

**数据库**：使用MySQL作为关系数据库管理系统，存储用户信息、订单数据等核心业务数据。

**缓存技术**：采用Redis作为缓存解决方案，提高数据处理速度和降低数据库负载。

**消息队列**：使用RabbitMQ来处理异步消息传递，保障系统的高可用性和可扩展性。

**配置管理和服务发现**：Nacos用作服务发现和配置管理，确保微服务架构下服务的健康和配置的动态更新。

**分布式事务管理**：集成Seata来处理分布式事务，确保跨服务的数据一致性。

**前端技术栈**：使用Vue.js作为主要的前端框架，结合相关生态如Vuex进行状态管理，利用Axios进行HTTP通信。

**容器化**：Docker用于应用容器化

**开发和测试环境**：推荐使用Linux作为主要的服务器操作系统，确保开发、测试和生产环境的一致性。

**开发涉及的工具和技术**：

**集成开发环境(IDE)**：IntelliJ IDEA，用于编写代码和管理项目。

**构建工具**：Maven，用于项目构建和依赖管理。

**API****测试工具**：如Apifox，用于测试和调试RESTful API。

**文档工具**：Apifox用于API文档的自动生成和展示。

 

（五）作品服务对象

 

**农民或农业生产者：**

这类群体是天天助农商城的供应方，他们通常拥有以下特性和需求：

1.直接市场接入：他们寻求更直接的渠道将产品销售给消费者，以获取更高的利润。

2.技术支持：需要技术和知识支持来提高农业生产效率和产品市场竞争力。

3.透明化交易：追求交易的透明化，确保自己的辛勤付出被公正对待。

4.可持续发展：希望通过可持续的农业实践获得长期稳定的经济回报。

5.社区和品牌建设：希望构建良好的品牌形象，增加消费者信任度。

**消费者：**

消费者是天天助农商城的需求方，他们的特点和需求包括：

1.新鲜和质量：对农产品的新鲜度和质量有较高要求，愿意为此支付合理的价格。

2.健康生活方式：倾向于选择有机、无公害或者低碳生态的食品，以符合健康的生活方式。

3.信息透明：希望能够了解产品的来源，包括生产环境和产品完整的供应链信息。

4.便捷购物体验：需要一个方便快捷的购物平台，提供一站式服务，包括搜索、比较、购买和快速配送。

5.参与和社区：愿意参与到社区中，与农民互动，了解农业知识，参与到相关活动中。

 

**服务对象定位说明：**

 

1.天天助农商城平台通过提供一个集成的电商解决方案来满足这两类群体的需求。平台不仅仅是一个简单的销售渠道，而是一个增值服务的生态系统，帮助农民提升产品价值，同时为消费者提供高质量的农产品和优质的购物体验。

2.通过技术创新，平台为农民提供了有效的市场推广工具和数据分析能力，帮助他们更好地了解市场趋势并优化产品供应。同时，消费者可以享受到从源头到餐桌的全程透明化信息服务，以及通过平台提供的知识共享和社区活动加深对农业的理解和参与。

3.定位于连接农民和消费者，天天助农商城不仅是促进农产品销售的平台，更是一个促进农业可持续发展和提升农业产业链价值的创新型服务平台。

 

（六）系统运行环境

 

**后端技术体系：**

Spring Boot：作为后端应用的主要框架，需要Java17的环境。

Spring Cloud：用于构建微服务架构，需要与Spring Boot兼容的版本。

MySQL：作为主要的关系数据库，需要至少5.7版本，建议使用8.0版本，配置根据数据量和访问量调整。

Redis：作为缓存服务器，需要稳定的网络环境和足够的内存资源。

RabbitMQ：作为消息队列服务，需要足够的磁盘空间和网络带宽。

Nacos：作为服务发现和配置中心，需要稳定的网络连接和足够的内存资源。

Seata：用于处理分布式事务，需要与Spring Cloud和MySQL兼容的版本。

Docker：用于容器化应用，需要Docker Engine和足够的存储空间。

 

**前端技术体系：**

1. Vue 3：

  \- 用途: 用于构建项目的核心架构和组件。

2. Vite:

  \- 用途: 用于开发环境中的构建和打包，提高开发和构建速度。

3. Element Plus:

  \- 用途: 用于快速构建漂亮的用户界面。

4. TypeScript:

  \- 用途: 提高代码的可靠性和可维护性，减少运行时错误。

5. Pinia:

  \- 用途: 管理应用的全局状态。

6. Vue Router:

  \- 用途: 管理应用的路由，定义页面间的导航逻辑。

7. Axios:

  \- 用途: 处理与后端的 HTTP 请求，进行数据交互。配置要求：

 

服务器硬件：至少需要多台服务器，配置根据服务类型和负载进行调整，包括CPU、内存和存储空间。

网络环境：需要稳定的互联网连接，带宽根据用户量和数据传输量进行配置。

安全措施：需要配置防火墙、数据加密等安全措施。

备份策略：需要定期备份数据库和重要文件，确保数据安全。

通过上述技术体系和配置要求，天天助农商城平台能够确保高效、稳定和安全的运行，满足农民和消费者的需求

 

**二、团队介绍**

（一）所属单位简介

所属单位：福州大学

创业团队介绍： 我们的团队原名为“天天向上”，后改为“天天助农”，由三名充满激情和创新精神的福州大学学生组成。尽管我们来自不同的年级和学院，但我们共同拥有对农业科技和电子商务的浓厚兴趣。在过去的合作中，我们成功搭建了一个博客系统，这一经历不仅锻炼了我们的技术能力，也加深了我们之间的团队协作和项目管理经验。

项目成员组成及具体分工：

 

余思衡 - 计算机科学与技术学院，大一学生

分工：担任项目的技术负责人，负责后端开发和系统架构设计。余思衡在之前的博客系统项目中展现了出色的编程能力和对复杂系统架构的理解，他的技术专长将为天天助农商城提供坚实的技术基础。

 

肖铭昊 - 计算机科学与技术学院，大一学生

分工： 负责项目的前端开发工作。主要是根据UI提供的界面设计用户界面，并且做了部分响应式设计，与后端商讨接口文档，并且开发相关脚本文件，保证页面运行、交互的稳定流畅以及较好的用户体验。

 

王笑睿 -梅努斯国际工程学院，大二学生

分工： 担任项目的UI设计，进行WEB端界面的UI原型设计。制作产品相关海报。

尽管我们的团队成员相对年轻，且在创业领域经验尚浅，但我们相信，通过我们的共同努力和互补的专业技能，我们能够克服挑战，将天天助农商城打造成一个成功的电子商务平台，为农民和消费者提供优质的服务。我们的团队致力于通过技术创新推动农业现代化，实现农业可持续发展，同时为消费者带来健康、新鲜的农产品。

 

（二）主要成员简介

 

**个人简介：余思衡**

 

我是一名来自福州大学计算机专业的大一学生，我对软件开发和计算机科学充满热情。虽然我目前还在我的学术旅程的初期阶段，但我已经通过参与一些关键的项目，积累了实际的编程经验和技术知识。我对技术的热情和对新挑战的勇气，推动我不断探索计算机科学的各个领域。

**技术能力和项目经验**

**Java****高级特性**：我对Java有深入的理解，特别是在使用Java编写自定义注解方面，我不仅掌握了语法，更理解其在实际开发中的应用，这在提升代码的可读性和灵活性方面发挥了关键作用。

**备忘录微服务项目**：我成功设计并实现了一个备忘录微服务，该项目采用了Spring Boot框架，展现了我在微服务架构设计和实现方面的能力。通过这个项目，我深入理解了微服务架构的优点和挑战，以及如何在实践中有效地应用。

**博客系统单体架构项目**：参与开发了一个基于Spring Boot的博客系统，该项目采用单体架构设计。通过这个项目，我深入了解了从前端到后端的完整开发流程，以及如何设计高效的后端服务。

**个人品质**

快速学习能力：作为计算机科学的新手，我已经证明了自己在短时间内掌握复杂概念和技术的能力。

团队合作：在参与项目的过程中，我学会了如何与团队成员有效沟通和协作，确保项目顺利进行。

创新思维：我始终保持对技术创新的追求，努力在项目中探索和实现新的解决方案。

**未来目标**

作为一名热衷于技术和创新的学生，我希望能够继续深入学习计算机科学的各个领域，并将我的学习和研究应用到实际的项目中，为解决现实世界的问题贡献自己的力量。我也期待有机会参与更多的竞赛和项目，以此来不断挑战自我，扩展我的技术视野。

 

**个人简介：王笑睿**

 

我是一名福州大学数字媒体技术专业的大二学生，在项目中主要担任UI设计、平面设计工作。我具备良好的沟通能力和团队精神，能够有效地与他人合作，解决问题并达成共识。具备较强的责任心和事业心，对未来充满憧憬，并愿意不断学习和提升自己，迎接新的挑战。我具备较强的自我管理能力，能够有效组织时间和资源，保证工作的高效和质量。

**项目经验**

**大学生服务外包创新创业大赛**：项目内容为教育教学数据中台的开发，进行教育数据的数据化整理与可视化呈现。担任产品经理、UI设计职务，进行WEB端的UI原型设计。进行项目内容的产品需求分析、竞品分析等工作。完成项目演示视频等展示内容制作。

**中国高校计算机大赛**：项目内容为教育教学考勤功能开发。担任UI设计职务，完成项目演示视频制作与PPT细化完善。

**相关技能** 熟练掌握PS、AI等软件进行平面设计 使用FIGMA等UI设计软件进行UI原型设计 使用PR、AE、达芬奇等软件进行视频的剪辑和制作 使用Procreate\PS进行板绘绘画 熟悉产品需求文档、竞品分析等产品经理工作

 

**个人简介：肖铭昊**

 

我是一名来自福州大学计算机类专业的大一学生，我对前端软件开发充满了兴趣。虽然我目前还在技术学习的初期阶段，但我已经通过参与一些项目的制作，积累了一定的实际编程经验和技术知识。我对技术的热情和对新挑战的勇气，推动我不断探索计算机科学的各个领域。

**技术能力和项目经验**

**Vue3**：对于前端框架Vue.js，不能说掌握，在一定程度上是有一定的理解，并且对于

模块化开发和组合式api也有所了解。以及Vue3相关的生态系统，有对Vue-Router、Pinia的使用等等。

**备忘录TodoList平台**：我成功按照需求和UI界面搭建了一个TodoList平台，在开发中使用到了Vue-Router技术，使得路由导航变得十分遍历；Pinia的全局状态管理，保证了数据的安全性和可扩展性，以及模块化开发；界面上使用了Elment-Plus使得交互界面交互性和观感性强。

**博客平台**：与后端协作开发一个基于Vue3的前后端分离项目，该项目使用了Axios库发送AJAX请求与后端通讯。通过这个项目，我深入了解了API的开发流程，以及如何与后端人员进行交流解决问题。

**个人品质**

快速的学习能力：作为计算机科学的新手，我已经证明了自己在短时间内学习复杂概念和技术的能力。

**团队合作**：在项目开发的过程中，我学会了如何与队友有效沟通和合作，提高开发效率和项目开发的顺利进行。

 

**解决问题的能力**：在开发中遇到问题，会自主追根溯源寻找问题所在，并将其改正。

**创新思维**：我始终保持对技术创新的追求，努力在项目中探索和实现新的解决方案。

**未来目标**

作为一名热衷于技术和创新的学生，我希望能够继续深入学习更深入的技术，并将我的学习和研究应用到实际的项目中，为解决实际生活中的问题贡献一份力量。

 

  