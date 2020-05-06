#version 400 core
out vec4 out_color;

in vec2 pass_texCoords;

uniform sampler2D texture_sampler;
void main() {
    out_color = texture(texture_sampler, pass_texCoords);
}