#version 300 es
precision mediump float;
uniform sampler2D uTextureUnit;
in vec2 vTexCoord;

//输出
out vec4 vFragColor;
uniform float time;

void main()
{
     vec4 result = vec4(1.0,1.0,1.0,1.0);
     vec2 translate = vec2(vTexCoord.x,vTexCoord.y);
     result = texture(uTextureUnit, vTexCoord);

     // Calculate modulo to decide even and odd row
     float div = 2.0;
     float num = floor(vTexCoord.y * 10.0);
     float odd = num - (div * floor(num/div));

     float t = mod(time,1.0);

     //Only do the odd number
     if( odd == 0.0){
          translate.x = translate.x - t;
          result = texture(uTextureUnit,translate);
          if(translate.x < 0.0){
               discard;
          }
     }
     else{
          translate.x = translate.x + t;
          result = texture(uTextureUnit,translate);
          if(translate.x > 1.0){
               discard;
          }
     }

     // Output to screen
     vFragColor = result;
}