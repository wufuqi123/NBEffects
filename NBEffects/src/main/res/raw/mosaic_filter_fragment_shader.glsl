#version 300 es
precision mediump float;
uniform sampler2D uTextureUnit;
in vec2 vTexCoord;

//输出
out vec4 vFragColor;
uniform vec3 iResolution;
uniform float mosaicSize;
uniform vec4 color;

void main(void){
     vec4 colorRead;
     vec2 xy = vec2(vTexCoord.x * iResolution.x, vTexCoord.y * iResolution.y);
     vec2 xyMosaic = vec2(floor(xy.x / mosaicSize) * mosaicSize, floor(xy.y / mosaicSize) * mosaicSize);
     vec2 xyFloor = vec2(floor(mod(xy.x, mosaicSize)), floor(mod(xy.y, mosaicSize)));
     vec2 uvMosaic = vec2(xyMosaic.x / iResolution.x, xyMosaic.y / iResolution.y);
     vFragColor = texture( uTextureUnit, uvMosaic);
}