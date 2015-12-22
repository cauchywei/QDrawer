module hello;
using java.lang.Math;

const PI = 3.1415926;

origin = (150,150);

func drawSin(xScale,yScale,t){
	for i from 0 to t*PI step PI/150 {
		draw(i*xScale,sin(i)*yScale);
	}
}

func drawCos(xScale,yScale,t){
	for i from 0 to t*PI step PI/150 {
		draw(i*xScale,cos(i)*yScale);
	}
}

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

for times from 0 to 6 step 1 {
	origin = (0,20);
        if(times % 2 == 0){
            	drawCos(15,20,10);
        }else{
            	drawSin(15,20,10);
        }
}

origin = (-250,250);
scale = 0.5;
for times from 0 to 8 step 1 {
	origin = (0,30); 
	rot = PI/4;
	drawSin(10,20,5);
}

origin = (450,0);


for i from 0 to 20*PI step PI/250 {
		draw(sin(i)*150,cos(i)*150);
		draw(
((1-7/10)*cos(i) + 7/10*cos(-i*(10/7-1)))*150,
((1-7/10)*sin(i) + 7/10*sin(-i*(10/7-1)))*150);
}
