<script setup>
const props = defineProps(['isActive']);
</script>

<template>
  <div>
    <div class="myCard">
      <div :style="props.isActive?'transform: rotateY(180deg);':''" class="innerCard">
        <div class="frontSide">
          <p class="title">Ai</p>

        </div>
        <div class="backSide">
          <p class="title">Ai</p>
        </div>
      </div>
    </div>
  </div>

</template>

<style scoped>
.myCard {
  background-color: transparent;
  width: 40px;
  height: 40px;
  perspective: 1000px;
}

.title {
  font-size: 18px;
  font-weight: 900;
  text-align: center;
  margin: 0;
}

.innerCard {
  position: relative;
  width: 100%;
  height: 100%;
  text-align: center;
  transition: transform 0.8s;
  transform-style: preserve-3d;
  cursor: pointer;
}



.frontSide,
.backSide {
  position: absolute;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
  width: 100%;
  height: 100%;
  -webkit-backface-visibility: hidden;
  backface-visibility: hidden;
  border-radius: 40px;
  color: white;
  box-shadow: 0 0 1px rgba(255, 255, 255, 0.5);
  font-weight: 700;
}

.frontSide,
.frontSide::before {
  background: linear-gradient(43deg, rgb(65, 88, 208) 0%, rgb(200, 80, 192) 46%, rgb(255, 204, 112) 100%);
}

.backSide,
.backSide::before {
  background-image: linear-gradient(160deg, #0093E9 0%, #80D0C7 100%);
}

.backSide {
  transform: rotateY(180deg);
}

.frontSide::before,
.backSide::before {
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  content: '';
  width: 108%;
  height: 102%;
  position: absolute;
  z-index: -1;
  border-radius: 0.4em;
  filter: blur(8px);
  animation: animate 5s linear infinite;
}

@keyframes animate {
  0% {
    opacity: 0.2;
  }

  68% {
    opacity: 0.8;
  }

  100% {
    opacity: 0.2;
  }
}
</style>