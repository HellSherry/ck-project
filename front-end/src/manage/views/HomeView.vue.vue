
<template>
  <div class="welcome-page">
    <header>
      <h1 class="title">创科教育管理系统</h1>
      <div class="subtitle">打造高效便捷的教育管理新体验</div>
    </header>

    <main>
      <section class="hero-section">
        <img src="assets1/home_hm_left.png" alt="创科教育管理系统Logo" class="welcome-image">
        <h2 class="welcome-text">欢迎来到创科教育管理系统</h2>
        <p class="description">
          这里集合了丰富的教育资源，科学的课程管理功能，以及全面的学生信息管理模块。
          让我们一起携手，为提高教育质量和效率努力前行！
        </p>
      </section>

      <!-- 欢迎动画/图片/视频等可以根据需求添加 -->

      <section class="action-buttons">

        <router-link to="/manage/papers" class="btn btn-primary">开始探索</router-link>
        <router-link to="/about" class="btn btn-secondary">了解我们</router-link>
      </section>
    </main>

  </div>
</template>

<style scoped>
.welcome-page {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;


  .title {
    font-size: 3rem;
    margin-bottom: 1rem;
    text-align: center;

  }

  .subtitle {
    font-size: 1.5rem;
    text-align: center;
    opacity: 0.7;
  }

  .hero-section {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    text-align: center;
    max-width: 600px;
    margin: 0 auto;
    padding: 4rem 2rem;
  }

  .welcome-image {
    width: 200px;
    height: auto;
    margin-bottom: 4rem;
  }

  .welcome-text {
    font-size: 2rem;
    margin-bottom: 0rem;
  }

  .description {
    font-size: 1rem;
    line-height: 1.5;
  }

  .action-buttons {
    display: flex;
    justify-content: center;
    gap: 1rem;
  }

  .copyright {
    font-size: 0.8rem;
    text-align: center;
    margin-top: 2rem;
    opacity: 0.7;
  }
}
</style>
<script setup lang="ts">
</script>