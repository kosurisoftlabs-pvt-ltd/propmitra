/*
 AngularJS v1.4.8
 (c) 2010-2015 Google, Inc. http://angularjs.org
 License: MIT
*/
(function(z,h,A){'use strict';function m(h){return["$animate",function(r){return{restrict:"AE",transclude:"element",terminal:!0,require:"^^ngMessages",link:function(n,f,a,g,l){var c=f[0],p,h=a.ngMessage||a.when;a=a.ngMessageExp||a.whenExp;var k=function(b){p=b?v(b)?b:b.split(/[\s,]+/):null;g.reRender()};a?(k(n.$eval(a)),n.$watchCollection(a,k)):k(h);var e,q;g.register(c,q={test:function(b){var a=p;b=a?v(a)?0<=a.indexOf(b):a.hasOwnProperty(b):void 0;return b},attach:function(){e||l(n,function(b){r.enter(b,
null,f);e=b;var a=e.$$attachId=g.getAttachId();e.on("$destroy",function(){e&&e.$$attachId===a&&(g.deregister(c),q.detach())})})},detach:function(){if(e){var b=e;e=null;r.leave(b)}}})}}}]}var v=h.isArray,w=h.forEach,x=h.isString,y=h.element;h.module("ngMessages",[]).directive("ngMessages",["$animate",function(h){function r(f,a){return x(a)&&0===a.length||n(f.$eval(a))}function n(f){return x(f)?f.length:!!f}return{require:"ngMessages",restrict:"AE",controller:["$element","$scope","$attrs",function(f,
a,g){function l(b,a){for(var d=a,f=[];d&&d!==b;){var c=d.$$ngMessageNode;if(c&&c.length)return k[c];d.childNodes.length&&-1==f.indexOf(d)?(f.push(d),d=d.childNodes[d.childNodes.length-1]):d=d.previousSibling||d.parentNode}}var c=this,p=0,m=0;this.getAttachId=function(){return m++};var k=this.messages={},e,q;this.render=function(b){b=b||{};e=!1;q=b;for(var p=r(a,g.ngMessagesMultiple)||r(a,g.multiple),d=[],k={},s=c.head,l=!1,m=0;null!=s;){m++;var t=s.message,u=!1;l||w(b,function(a,b){!u&&n(a)&&t.test(b)&&
!k[b]&&(u=k[b]=!0,t.attach())});u?l=!p:d.push(t);s=s.next}w(d,function(b){b.detach()});d.length!==m?h.setClass(f,"ng-active","ng-inactive"):h.setClass(f,"ng-inactive","ng-active")};a.$watchCollection(g.ngMessages||g["for"],c.render);this.reRender=function(){e||(e=!0,a.$evalAsync(function(){e&&q&&c.render(q)}))};this.register=function(b,a){var d=p.toString();k[d]={message:a};var e=f[0],g=k[d];c.head?(e=l(e,b))?(g.next=e.next,e.next=g):(g.next=c.head,c.head=g):c.head=g;b.$$ngMessageNode=d;p++;c.reRender()};
this.deregister=function(b){var a=b.$$ngMessageNode;delete b.$$ngMessageNode;var d=k[a];(b=l(f[0],b))?b.next=d.next:c.head=d.next;delete k[a];c.reRender()}}]}}]).directive("ngMessagesInclude",["$templateRequest","$document","$compile",function(h,m,n){return{restrict:"AE",require:"^^ngMessages",link:function(f,a,g){var l=g.ngMessagesInclude||g.src;h(l).then(function(c){n(c)(f,function(c){a.after(c);c=y(m[0].createComment(" ngMessagesInclude: "+l+" "));a.after(c);a.remove()})})}}}]).directive("ngMessage",
m("AE")).directive("ngMessageExp",m("A"))})(window,window.angular);
//# sourceMappingURL=angular-messages.min.js.map

var app = angular.module("propvalidation", ["ngMessages"]);
app.controller("validateCtrl", ["$scope", function($scope) {
  $scope.newUser = {
    user: '',
    email: '',
    phone: '',
    password:'',
    city:'',
    address:'',
    /*acceptConds: false,
    subscribeNews: false*/
  };
  $scope.valid1='myForm.user.$invalid || myForm.email.$invalid || myForm.phone.$invalid || myForm.password.$invalid || myForm.city.$invalid || myForm.address.$invalid';
  $scope.valid='myForm.user.$invalid || myForm.password.$invalid';
  $scope.valid2='myForm.email.$invalid';
  $scope.submitForm = function() {
    angular.forEach($scope.myForm.$error.required, function(field) {
      field.$setDirty();
    });
  };
  $scope.regularClick = function() {
    alert("OJO: Valida pero no hace Submit!");
  };
  $scope.showMessage = function(input) {
    var show = input.$invalid && (input.$dirty || input.$touched);
    return show;
  };
$scope.showPassword = false;

  $scope.toggleShowPassword = function() {
    $scope.showPassword = !$scope.showPassword;
  }
}]);

app.directive('numbersOnly', function () {
    return {require: 'ngModel',
link: function (scope, element, attr, ngModelCtrl){
function fromUser(text) {
        if (text) {var transformedInput = text.replace(/[^0-9]/g, '');
      if (transformedInput !== text) {
  ngModelCtrl.$setViewValue(transformedInput);ngModelCtrl.$render();
                    }return transformedInput;
                }else{
                  alert("enter phone number");
                }return undefined;
              }ngModelCtrl.$parsers.push(fromUser);}};});