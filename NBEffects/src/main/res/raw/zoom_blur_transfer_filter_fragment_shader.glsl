#version 300 es
precision mediump float;
uniform sampler2D uTextureUnit;
in vec2 vTexCoord;

//输出
out vec4 vFragColor;
uniform float time;

const float strength = 0.3;
const float PI = 3.141592653589793;

float Linear_ease(in float begin, in float change, in float duration, in float time) {
     return change * time / duration + begin;
}

float Exponential_easeInOut(in float begin, in float change, in float duration, in float time) {
     if (time == 0.0)
     return begin;
     else if (time == duration)
     return begin + change;
     time = time / (duration / 2.0);
     if (time < 1.0)
     return change / 2.0 * pow(2.0, 10.0 * (time - 1.0)) + begin;
     return change / 2.0 * (-pow(2.0, -10.0 * (time - 1.0)) + 2.0) + begin;
}

float Sinusoidal_easeInOut(in float begin, in float change, in float duration, in float time) {
     return -change / 2.0 * (cos(PI * time / duration) - 1.0) + begin;
}

float random(in vec3 scale, in float seed) {
     return fract(sin(dot(gl_FragCoord.xyz + seed, scale)) * 43758.5453 + seed);
}

vec4 crossFade(in vec2 uv, in float dissolve) {
     vec4 colore = vec4(0.0,0.0,0.0,0.0);
     return mix(texture(uTextureUnit, uv), colore, dissolve);
}

void main()
{
     float progress = sin(time*0.5) * 0.5 + 0.5;
     vec2 center = vec2(Linear_ease(0.5, 0.0, 1.0, progress),0.5);
     float dissolve = Exponential_easeInOut(0.0, 1.0, 1.0, progress);

     float strength = Sinusoidal_easeInOut(0.0, strength, 0.5, progress);

     vec4 colorx = vec4(0.0);
     float total = 0.0;
     vec2 toCenter = center - vTexCoord;

     float offset = random(vec3(12.9898, 78.233, 151.7182), 0.0)*0.5;

     for (float t = 0.0; t <= 20.0; t++) {
          float percent = (t + offset) / 20.0;
          float weight = 1.0 * (percent - percent * percent);
          colorx += crossFade(vTexCoord + toCenter * percent * strength, dissolve) * weight;
          total += weight;
     }
     vFragColor = colorx / total;
}