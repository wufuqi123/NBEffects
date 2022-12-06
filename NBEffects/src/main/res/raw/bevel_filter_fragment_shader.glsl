#version 300 es
precision mediump float;
uniform sampler2D uTextureUnit;
in vec2 vTexCoord;

//输出
out vec4 vFragColor;
uniform vec4 filterArea;

uniform float transformX;
uniform float transformY;
uniform vec3 lightColor;
uniform float lightAlpha;
uniform vec3 shadowColor;
uniform float shadowAlpha;

void main(void) {
     vec2 transform = vec2(1.0 / filterArea) * vec2(transformX, transformY);
     vec4 color = texture(uTextureUnit, vTexCoord);
     float light = texture(uTextureUnit, vTexCoord - transform).a;
     float shadow = texture(uTextureUnit, vTexCoord + transform).a;

     color.rgb = mix(color.rgb, lightColor, clamp((color.a - light) * lightAlpha, 0.0, 1.0));
     color.rgb = mix(color.rgb, shadowColor, clamp((color.a - shadow) * shadowAlpha, 0.0, 1.0));
     vFragColor = vec4(color.rgb * color.a, color.a);
}
