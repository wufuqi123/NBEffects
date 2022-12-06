#version 300 es
precision mediump float;
uniform sampler2D uTextureUnit;
in vec2 vTexCoord;

//输出
out vec4 vFragColor;
uniform float time;

#define SAMPLES 10
#define SHARPNESS 4.0
#define SAMPLES_F float(SAMPLES)
#define TAU  6.28318530718
#define HASHSCALE1 443.8975

float hash13(vec3 p3)
{
     p3  = fract(p3 * HASHSCALE1);
     p3 += dot(p3, p3.yzx + 19.19);
     return fract((p3.x + p3.y) * p3.z);
}

void main(void){
     float r = 0.05 + 0.05 * sin(time*.2*TAU+4.3);

     float d = (r * 2.0 * vTexCoord.x) / SAMPLES_F;
     float lod = log2(max(d / SHARPNESS, 1.0));

     vec4 colorRead = vec4(0.0, 0.0, 0.0, 0.0);
     for (int i = 0; i < SAMPLES; ++i)
     {
          float fi = float(i);
          float rnd = hash13(vec3(vTexCoord.xy, fi));
          float f = (fi + rnd) / SAMPLES_F;
          f = (f * 2.0 - 1.0) * r;
          colorRead += texture(uTextureUnit, vTexCoord + vec2(f, 0.0), lod);
     }
     colorRead = colorRead / SAMPLES_F;
     vFragColor = colorRead;

     vFragColor.w = 2.25 - time;
}