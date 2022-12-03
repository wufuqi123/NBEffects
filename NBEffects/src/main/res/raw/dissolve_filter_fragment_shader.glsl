#version 300 es
precision mediump float;
uniform sampler2D uTextureUnit;
in vec2 vTexCoord;

//输出
out vec4 vFragColor;

uniform float dissolve;

void main(void){
    vec4 c = texture(uTextureUnit,vTexCoord);
    if(c.g <= dissolve)
    {
        discard;
    }
    vFragColor = c;
}