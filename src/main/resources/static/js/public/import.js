var ZBRangeSlider = function(id) { 
  var self = this;
  var startX = 0, x = 0;
  // retrieve touch button
  var slider     = document.getElementById(id)
  var touchLeft  = slider.querySelector('.slider-touch-left');
  var touchRight = slider.querySelector('.slider-touch-right');
  var lineSpan   = slider.querySelector('.slider-line span');
  // get some properties
  var min   = parseFloat(slider.getAttribute('se-min'));
  var max   = parseFloat(slider.getAttribute('se-max'));
  // retrieve default values
  var defaultMinValue = min;
  if(slider.hasAttribute('se-min-value'))
  {
    defaultMinValue = parseFloat(slider.getAttribute('se-min-value'));  
  }
  var defaultMaxValue = max;
  if(slider.hasAttribute('se-max-value'))
  {
    defaultMaxValue = parseFloat(slider.getAttribute('se-max-value'));  
  }
  // check values are correct
  if(defaultMinValue < min)
  {
    defaultMinValue = min;
  }
  if(defaultMaxValue > max)
  {
    defaultMaxValue = max;
  }
  if(defaultMinValue > defaultMaxValue)
  {
    defaultMinValue = defaultMaxValue;
  }
  var step  = 0.0;
  if (slider.getAttribute('se-step'))
  {
    step  = Math.abs(parseFloat(slider.getAttribute('se-step')));
  }
  // usefull values, min, max, normalize fact is the width of both touch buttons
  var normalizeFact = 26;
  var maxX = slider.offsetWidth - touchRight.offsetWidth;
  var selectedTouch = null;
  var initialValue = (lineSpan.offsetWidth - normalizeFact);
  self.slider = slider;
  self.reset = function() {
    touchLeft.style.left = '0px';
    touchRight.style.left = (slider.offsetWidth - touchLeft.offsetWidth) + 'px';
    lineSpan.style.marginLeft = '0px';
    lineSpan.style.width = (slider.offsetWidth - touchLeft.offsetWidth) + 'px';
    startX = 0;
    x = 0;
    slider.setAttribute('se-min-value', min);
    slider.setAttribute('se-max-value', max);
  };
  self.setMinValue = function(minValue)
  {
    var ratio = (defaultMaxValue / (max - min))
    lineSpan.style.marginLeft = (ratio * initialValue) + 'px';
    lineSpan.style.width = (touchRight.offsetLeft - touchLeft.offsetLeft) + 'px';
  }
  // initial reset
  self.reset();
  // setup touch/click events
  function onStart(event) {
    // Prevent default dragging of selected content
    event.preventDefault();
    var eventTouch = event;
    if (event.touches)
    {
      eventTouch = event.touches[0];
    }
    if(this === touchLeft)
    {
      x = touchLeft.offsetLeft;
    }
    else
    {
      x = touchRight.offsetLeft;
    }
    startX = eventTouch.pageX - x;
    selectedTouch = this;
    document.addEventListener('mousemove', onMove);
    document.addEventListener('mouseup', onStop);
    document.addEventListener('touchmove', onMove);
    document.addEventListener('touchend', onStop)
  }
  function onMove(event) {
    var eventTouch = event;
    if (event.touches)
    {
      eventTouch = event.touches[0];
    }
    x = eventTouch.pageX - startX;
    if (selectedTouch === touchLeft)
    {
      if(x > (touchRight.offsetLeft - selectedTouch.offsetWidth + 10))
      {
        x = (touchRight.offsetLeft - selectedTouch.offsetWidth + 10)
      }
      else if(x < 0)
      {
        x = 0;
      }
      selectedTouch.style.left = x + 'px';
    }
    else if (selectedTouch === touchRight)
    {
      if(x < (touchLeft.offsetLeft + touchLeft.offsetWidth - 10))
      {
        x = (touchLeft.offsetLeft + touchLeft.offsetWidth - 10)
      }
      else if(x > maxX)
      {
        x = maxX;
      }
      selectedTouch.style.left = x + 'px';
    }
    // update line span
    lineSpan.style.marginLeft = touchLeft.offsetLeft + 'px';
    lineSpan.style.width = (touchRight.offsetLeft - touchLeft.offsetLeft) + 'px';
    // write new value
    calculateValue();
    // call on change
    if(slider.getAttribute('on-change'))
    {
      var fn = new Function('min, max', slider.getAttribute('on-change'));
      fn(slider.getAttribute('se-min-value'), slider.getAttribute('se-max-value'));
    }
    if(self.onChange)
    {
      self.onChange(slider.getAttribute('se-min-value'), slider.getAttribute('se-max-value'));
    }}
  function onStop(event) {
    document.removeEventListener('mousemove', onMove);
    document.removeEventListener('mouseup', onStop);
    document.removeEventListener('touchmove', onMove);
    document.removeEventListener('touchend', onStop);
    selectedTouch = null;
    // write new value
    calculateValue();
    // call did changed
    if(slider.getAttribute('did-changed'))
    {
      var fn = new Function('min, max', slider.getAttribute('did-changed'));
      fn(slider.getAttribute('se-min-value'), slider.getAttribute('se-max-value'));
    }
    if(self.didChanged){self.didChanged(slider.getAttribute('se-min-value'), slider.getAttribute('se-max-value'));}}
  
  function calculateValue() {
    var newValue = (lineSpan.offsetWidth - normalizeFact) / initialValue;
    var minValue = lineSpan.offsetLeft / initialValue;
    var maxValue = minValue + newValue;
    var minValue = minValue * (max - min) + min;
    var maxValue = maxValue * (max - min) + min;
    console.log(step);
    if (step !== 0.0)
    {
      var multi = Math.floor((minValue / step));
      minValue = step * multi;
      
      multi = Math.floor((maxValue / step));
      maxValue = step * multi;
    }
    slider.setAttribute('se-min-value', minValue);
    slider.setAttribute('se-max-value', maxValue);
  }
  // link events
  touchLeft.addEventListener('mousedown', onStart);
  touchRight.addEventListener('mousedown', onStart);
  touchLeft.addEventListener('touchstart', onStart);
  touchRight.addEventListener('touchstart', onStart);
  
};
// -------------------
// How to use? 
var newRangeSlider = new ZBRangeSlider('my-slider');
newRangeSlider.onChange = function(min, max)
{
console.log(min, max, this);
document.getElementById('result').innerHTML = 'Min: ' + min + ' Max: ' + max;
}
newRangeSlider.didChanged = function(min, max)
{
  console.log(min,max, this);
  document.getElementById('result').innerHTML = 'Min: ' + min + ' Max: ' + max;
}








/*end range*/

/*~~~~~~~range second~~~~~*/
// THIS IS THE RANGE SLIDER LOGIC DO NOT CHANGE !!
var ZBRangeSlider = function(id) { 
  var self = this;
  var startX = 0, x = 0;
  // retrieve touch button
  var slider     = document.getElementById(id)
  var touchLeft  = slider.querySelector('.slider-touch-left');
  var touchRight = slider.querySelector('.slider-touch-right');
  var lineSpan   = slider.querySelector('.slider-line span');
  // get some properties
  var min   = parseFloat(slider.getAttribute('se-min'));
  var max   = parseFloat(slider.getAttribute('se-max'));
  // retrieve default values
  var defaultMinValue = min;
  if(slider.hasAttribute('se-min-value'))
  {
    defaultMinValue = parseFloat(slider.getAttribute('se-min-value'));  
  }
  var defaultMaxValue = max;
  
  if(slider.hasAttribute('se-max-value'))
  {
    defaultMaxValue = parseFloat(slider.getAttribute('se-max-value'));  
  }
  // check values are correct
  if(defaultMinValue < min)
  {defaultMinValue = min;}
  if(defaultMaxValue > max){defaultMaxValue = max;}
  if(defaultMinValue > defaultMaxValue)
  {defaultMinValue = defaultMaxValue;}
  
  var step  = 0.0;
  
  if (slider.getAttribute('se-step'))
  {
    step  = Math.abs(parseFloat(slider.getAttribute('se-step')));
  }
  // usefull values, min, max, normalize fact is the width of both touch buttons
  var normalizeFact = 26;
  var maxX = slider.offsetWidth - touchRight.offsetWidth;
  var selectedTouch = null;
  var initialValue = (lineSpan.offsetWidth - normalizeFact);
  self.slider = slider;
  self.reset = function() {
    touchLeft.style.left = '0px';
    touchRight.style.left = (slider.offsetWidth - touchLeft.offsetWidth) + 'px';
    lineSpan.style.marginLeft = '0px';
    lineSpan.style.width = (slider.offsetWidth - touchLeft.offsetWidth) + 'px';
    startX = 0;
    x = 0;
    slider.setAttribute('se-min-value', min);
    slider.setAttribute('se-max-value', max);
  };
  self.setMinValue = function(minValue)
  {
    var ratio = (defaultMaxValue / (max - min))
    lineSpan.style.marginLeft = (ratio * initialValue) + 'px';
    lineSpan.style.width = (touchRight.offsetLeft - touchLeft.offsetLeft) + 'px';
  }
  // initial reset
  self.reset();
  // setup touch/click events
  function onStart(event) { 
    // Prevent default dragging of selected content
    event.preventDefault();
    var eventTouch = event;if (event.touches)
    {eventTouch = event.touches[0];
    }
    if(this === touchLeft)
    {
      x = touchLeft.offsetLeft;
    }
    else
    {
      x = touchRight.offsetLeft;
    }
    startX = eventTouch.pageX - x;
    selectedTouch = this;
    document.addEventListener('mousemove', onMove);
    document.addEventListener('mouseup', onStop);
    document.addEventListener('touchmove', onMove);
    document.addEventListener('touchend', onStop);
  }
  
  function onMove(event) {
    var eventTouch = event;

    if (event.touches)
    {
      eventTouch = event.touches[0];
    }

    x = eventTouch.pageX - startX;
    
    if (selectedTouch === touchLeft)
    {
      if(x > (touchRight.offsetLeft - selectedTouch.offsetWidth + 10))
      {
        x = (touchRight.offsetLeft - selectedTouch.offsetWidth + 10)
      }
      else if(x < 0)
      {
        x = 0;
      }
      selectedTouch.style.left = x + 'px';
    }
    else if (selectedTouch === touchRight)
    {
      if(x < (touchLeft.offsetLeft + touchLeft.offsetWidth - 10))
      {
        x = (touchLeft.offsetLeft + touchLeft.offsetWidth - 10)
      }
      else if(x > maxX)
      {
        x = maxX;
      }
      selectedTouch.style.left = x + 'px';
    }
    // update line span
    lineSpan.style.marginLeft = touchLeft.offsetLeft + 'px';
    lineSpan.style.width = (touchRight.offsetLeft - touchLeft.offsetLeft) + 'px';
    
    // write new value
    calculateValue();
    
    // call on change
    if(slider.getAttribute('on-change'))
    {
      var fn = new Function('min, max', slider.getAttribute('on-change'));
      fn(slider.getAttribute('se-min-value'), slider.getAttribute('se-max-value'));
    }
    
  if(self.onChange){
  self.onChange(slider.getAttribute('se-min-value'), slider.getAttribute('se-max-value'));
    }}
  function onStop(event) {
    document.removeEventListener('mousemove', onMove);
    document.removeEventListener('mouseup', onStop);
    document.removeEventListener('touchmove', onMove);
    document.removeEventListener('touchend', onStop);
    selectedTouch = null;
    // write new value
    calculateValue();
    // call did changed
    if(slider.getAttribute('did-changed'))
    {
      var fn = new Function('min, max', slider.getAttribute('did-changed'));
      fn(slider.getAttribute('se-min-value'), slider.getAttribute('se-max-value'));
    }
    if(self.didChanged){
self.didChanged(slider.getAttribute('se-min-value'), slider.getAttribute('se-max-value'));
}
}
  function calculateValue() {
    var newValue = (lineSpan.offsetWidth - normalizeFact) / initialValue;
    var minValue = lineSpan.offsetLeft / initialValue;
    var maxValue = minValue + newValue;
    var minValue = minValue * (max - min) + min;
    var maxValue = maxValue * (max - min) + min;
    
    console.log(step);
    if (step !== 0.0)
    {var multi = Math.floor((minValue / step));minValue = step * multi;
      multi = Math.floor((maxValue / step));
      maxValue = step * multi;} 
    slider.setAttribute('se-min-value', minValue);
    slider.setAttribute('se-max-value', maxValue);
  }
// link events
  touchLeft.addEventListener('mousedown', onStart);
  touchRight.addEventListener('mousedown', onStart);
  touchLeft.addEventListener('touchstart', onStart);
  touchRight.addEventListener('touchstart', onStart);};
// -------------------
// How to use? 
var newRangeSlider = new ZBRangeSlider('my-slider1');
newRangeSlider.onChange = function(min, max)
{console.log(min, max, this);
document.getElementById('result1').innerHTML = 'Min: ' + min + ' Max: ' + max;
}
newRangeSlider.didChanged = function(min, max)
{console.log(min,max, this);
document.getElementById('result1').innerHTML = 'Min: ' + min + ' Max: ' + max;}



/*===============start website==================*/
if (typeof Storage !== "undefined") {
  if (localStorage.visitcount) {
    document.getElementById("ip").innerHTML =
      "website counter" +
      localStorage.visitcount +
      " times before.";
    localStorage.visitcount = Number(localStorage.visitcount) + 1;
  } else {
    localStorage.visitcount = 1;
    document.getElementById("ip").innerHTML =
    "This is Firstime counter.";
  }
  // document.getElementById("result").innerHTML = "You have clicked the button " + localStorage.clickcount + " time(s).";
} else {
   alert("Sorry, your browser does not support web storage.  Changes will not be saved");
  document.getElementById("ip").innerHTML =
    "Sorry, your browser does not support web storage...";
}
console.log("localstorage visit count now: " + localStorage.visitcount);

// localStorage.clear();
/*============end=======*/
  /**
 * Get the user IP throught the webkitRTCPeerConnection
 * @param onNewIP {Function} listener function to expose the IP locally
 * @return undefined
 */
function getUserIP(onNewIP) { //  onNewIp - your listener function for new IPs
    //compatibility for firefox and chrome    
    var myPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
    var pc = new myPeerConnection({
        iceServers: []
    }),
    noop = function() {},
    localIPs = {},
    ipRegex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g,
    key;
    function iterateIP(ip) {
        if (!localIPs[ip]) onNewIP(ip);
        localIPs[ip] = true;
    }
     //create a bogus data channel
    pc.createDataChannel("");

    // create offer and set local description
    pc.createOffer().then(function(sdp) {
        sdp.sdp.split('\n').forEach(function(line) {
            if (line.indexOf('candidate') < 0) return;
            line.match(ipRegex).forEach(iterateIP);
        });        
        pc.setLocalDescription(sdp, noop, noop);
    }).catch(function(reason) {
        // An error occurred, so handle the failure to connect
    });
    //listen for candidate events
    pc.onicecandidate = function(ice) {
        if (!ice || !ice.candidate || !ice.candidate.candidate || !ice.candidate.candidate.match(ipRegex)) return;
        ice.candidate.candidate.match(ipRegex).forEach(iterateIP);
    };
}
// Usage
getUserIP(function(ip){
 console.log("visiter ip address this is a awesome to find ip'is"+ip+" ip count count of 1");
document.getElementById("ip").innerHTML=ip;
});

