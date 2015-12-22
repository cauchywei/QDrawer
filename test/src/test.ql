module hello;
using java.lang.Math;

const PI = 3.1415926;

origin = (150,150);

///////Lollipop

for i from 0 to 20*PI step PI/150 {
	draw(i*sin(i),i*cos(i));
}

for i from 0 to 45 step 0.2 {
	draw(i,i*3);
}

scale = 2;
draw("Lollipop",-20,80);
scale = 0.5;

///////Lollipop end

origin = (250,0);

for times from 0 to 4 step 1 {
	origin = (0,20);
	for i from 0 to 6*PI step PI/200 {

		draw(i*15,20*cos(i));
	}
}

origin = (-250,300);
scale = 0.5;
for times from 0 to 8 step 1 {
	origin = (0,20);
	rot = PI/4;
	for i from 0 to 5*PI step PI/150 {
		draw(i*10,20*cos(i));
	}
}

origin = (450,0);


for i from 0 to 20*PI step PI/250 {
		draw(sin(i)*150,cos(i)*150);
		draw(
((1-7/10)*cos(i) + 7/10*cos(-i*(10/7-1)))*150,
((1-7/10)*sin(i) + 7/10*sin(-i*(10/7-1)))*150);
}
