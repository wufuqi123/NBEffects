#version 300 es
precision mediump float;
uniform sampler2D uTextureUnit;
in vec2 vTexCoord;

//输出
out vec4 vFragColor;
uniform vec4 color;
uniform float bluramount;

vec4 draw(vec2 uv) {
     return texture(uTextureUnit, uv);
}

float grid(float var, float size) {
     return floor(var*size)/size;
}

float rand(vec2 co){
     return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}
void mainImage( out vec4 vFragColor, in vec2 vTexCoord )
{
     vec2 uv = vTexCoord.xy;
     vec4 blurred_image = vec4(0.);
     #define repeats 5.
     for (float i = 0.; i < repeats; i++) {
          vec2 q = vec2(cos(degrees((i/repeats)*360.)),sin(degrees((i/repeats)*360.))) * (rand(vec2(i,uv.x+uv.y))+bluramount);
          vec2 uv2 = uv+(q*bluramount);
          blurred_image += draw(uv2)/2.;
          q = vec2(cos(degrees((i/repeats)*360.)),sin(degrees((i/repeats)*360.))) * (rand(vec2(i+2.,uv.x+uv.y+24.))+bluramount);
          uv2 = uv+(q*bluramount);
          blurred_image += draw(uv2)/2.;
     }
     blurred_image /= repeats;
     vFragColor = vec4(blurred_image);
}
void main(void){
     mainImage(vFragColor, vTexCoord.xy);
}