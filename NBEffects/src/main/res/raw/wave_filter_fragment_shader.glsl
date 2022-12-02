#version 300 es
precision mediump float;
uniform sampler2D uTextureUnit;
in vec2 vTexCoord;
//输出
uniform float iTime;
uniform vec2 iOffset;

//输出
out vec4 vFragColor;

void main() {
    vec2 coord = vTexCoord;
    coord.x += (sin(coord.y * 30.0 + iTime * 3.0) / 30.0 * iOffset[0]);
    coord.y += (sin(coord.x * 30.0 + iTime * 3.0) / 30.0 * iOffset[1]);
    vFragColor = texture(uTextureUnit, coord);
}