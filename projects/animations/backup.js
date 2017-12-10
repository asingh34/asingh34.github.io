var canvas = document.getElementById('canvas');
var ctx = canvas.getContext('2d');
var pi = Math.PI;
var raf;

var ball = {
  x: 720,
  y: 900,
  radius: 150,
  color: 'orange',
  draw: function() {
    ctx.beginPath();
    ctx.arc(this.x, this.y, this.radius, 0, Math.PI*2, true);
    ctx.closePath()
    ctx.fillStyle= this.color;
    ctx.fill();   
  },
  getRayCoordinates: function(theta, length) {
    console.log('getRayCoords', theta, length);
    var result = {};
    result.origX = this.x;
    result.origY = this.y;
    result.x1 = this.radius * Math.cos(theta);
    result.y1 = -(this.radius * Math.sin(theta));
    result.x2 = (this.radius + length) * Math.cos(theta);
    result.y2 = -((this.radius + length) * Math.sin(theta));
    console.log('result', result)
    return result;  
  }
}

var line = {
  draw: function(coords,color) {
    ctx.save();
    ctx.translate(coords.origX, coords.origY);
    ctx.beginPath();
    ctx.moveTo(coords.x1, coords.y1);
    ctx.lineTo(coords.x2,coords.y2);
    ctx.closePath();
    ctx.strokeStyle = color || 'yellow' ;
    ctx.stroke();
    ctx.restore();

  }
}

var nPoints = 100;
var rPoints = 10000;
var rCounter = 0;
var counter = 0;
var smallCounter = 0;
var bigCounter = 0;
var smallLimit = 50;
var bigLimit= 50; 
var smallDirection = 0;
var bigDirection = 0;
var angleCounter = 0; 
var ballY = canvas.height/2;


function animate(){
  ctx.clearRect(0,0, canvas.width, canvas.height);
    
  ball.y = ball.y - ((ball.y-ballY)*rCounter/rPoints)
  ball.draw()

  var toggle=0;
  var delta = 1/16;
  for (var i = 0; i <=2;  i = i+delta) { 

    if (toggle===0){
      line.draw(ball.getRayCoordinates((i+delta*angleCounter/nPoints)*pi,100*counter/nPoints+smallCounter),'yellow')
      toggle=1
    }

    else{
      line.draw(ball.getRayCoordinates((i+delta*angleCounter/nPoints)*pi,150*counter/nPoints-bigCounter),'orange')
      toggle=0
    }
  }

  if (rCounter < rPoints){
    rCounter++;
  }

  if(counter < nPoints){
    counter++;
  }
  else{
    if(smallDirection === 0){
      if(smallCounter < smallLimit){
        smallCounter++;
      }
      else{
        smallDirection = 1;
      }
    }
    else{
      if(smallCounter > 0){
        smallCounter--;
      }
      else{
        smallDirection = 0;
      }

    }

    if(bigDirection === 0){
      if(bigCounter < bigLimit){
        bigCounter++;
      }
      else{
        bigDirection = 1;
      }
    }
    else{
      if(bigCounter > 0){
        bigCounter--;
      }
      else{
        bigDirection = 0;
      }

    }
    if(angleCounter < 2*nPoints){
      angleCounter++;
    } 
    else{
      angleCounter=0;
    }
  }




  raf = window.requestAnimationFrame(animate);
}

animate();



