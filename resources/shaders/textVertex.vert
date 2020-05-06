#version 400 core

layout (location = 0) in vec2 position;
layout (location = 1) in vec2 texCoords;

uniform mat4 model;
uniform mat4 projection;

out vec2 pass_texCoords;

void main() {
    gl_Position =  projection * model * vec4(position.x, position.y, 0.0f, 1.0f);
    pass_texCoords = texCoords;
}