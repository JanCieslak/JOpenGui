#version 400 core

layout (location = 0) in vec2 position;

uniform mat4 model;
uniform mat4 projection;

void main() {
    gl_Position =  projection * model * vec4(position.x, position.y, 0.0f, 1.0f);
}