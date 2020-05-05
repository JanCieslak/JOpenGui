#version 400 core

uniform vec3 color = vec3(1.0, 0.0, 1.0);

out vec4 out_color;

void main() {
    out_color = vec4(color, 1.0f);
}