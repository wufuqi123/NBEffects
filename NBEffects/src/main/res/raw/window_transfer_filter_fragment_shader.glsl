#version 300 es
precision mediump float;
uniform sampler2D uTextureUnit;
in vec2 vTexCoord;

//输出
out vec4 vFragColor;

uniform float transfer;

void main()
{

     float dur = 2.;
     float dim = 7.;
     vec2 v = vTexCoord;
     v.y=1.-v.y;
     vec2 x = mod(1.-v.xx, 1./dim)+floor(v*dim)/dim;
     float a = .5*(abs(x.x)+abs(x.y));
     float b = fract(transfer/dur);
     a=a>b?0.:1.;
     bool mt = mod(floor(transfer/dur),2.)==0.;
     float cd = a;
     if (mt)
     {
          cd=1.-cd;
     }
     vec4 colore = vec4(0.5,0.2,0.3, 0.01);
     vFragColor = mix(vec4(a),(mix(texture(uTextureUnit, vTexCoord), colore, cd)), 1.);

}