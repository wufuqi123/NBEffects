#version 300 es
precision mediump float;
uniform sampler2D uTextureUnit;

uniform vec3 color;
in vec2 vTexCoord;

//输出
out vec4 vFragColor;
void main() {
    vec4 vTextureColor = texture(uTextureUnit,vTexCoord);
    float vFilterColor = (color.x * vTextureColor.r + color.y * vTextureColor.g + color.z * vTextureColor.b);
    vFragColor = vec4(vFilterColor, vFilterColor, vFilterColor, vTextureColor.a);
}