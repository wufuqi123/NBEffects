#version 300 es
precision mediump float;
uniform sampler2D uTextureUnit;
in vec2 vTexCoord;

//输出
out vec4 vFragColor;
uniform vec3 iResolution;
uniform vec2 iCenter;

void mainImage( out vec4 vFragColor, in vec2 vTexCoord )
{
     //const float Strength = 0.125;
     const float Strength = 0.015;
     const int Samples = 64; //multiple of 2

     vec2 uv = vTexCoord.xy;

     vec2 dir = (vTexCoord.xy-iCenter.xy);

     vec4 colorRead = vec4(0.0,0.0,0.0,0.0);

     for (int i = 0; i < Samples; i += 2) //operating at 2 samples for better performance
     {
          colorRead += texture(uTextureUnit,uv+float(i)/float(Samples)*dir*Strength);
          colorRead += texture(uTextureUnit,uv+float(i+1)/float(Samples)*dir*Strength);
     }

     vFragColor = colorRead/float(Samples);
}
void main(void){
     mainImage(vFragColor, vTexCoord);
}