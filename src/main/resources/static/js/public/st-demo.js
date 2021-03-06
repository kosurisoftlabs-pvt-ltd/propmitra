;(function() {
  'use strict';
  $(activate);
  function activate() {
    $('.nav-tabs')
      .scrollingTabs()
      .on('ready.scrtabs', function() {
        $('.tab-content').show();
      });
  }
}());
/*slider*/
 (function(){
  // setup your carousels as you normally would using JS
  // or via data attributes according to the documentation
  // https://getbootstrap.com/javascript/#carousel
  $('#carousel218').carousel({ interval:false});
  $('#carouselABC').carousel({ interval: false });
}());
(function(){
  $('.carousel-showmanymoveone .item').each(function(){
    var itemToClone = $(this);
    for (var i=1;i<4;i++) {
      itemToClone = itemToClone.next();

      // wrap around if at end of item collection
      if (!itemToClone.length) {
        itemToClone = $(this).siblings(':first');
      }
      // grab item, clone, add marker class, add to collection
      itemToClone.children(':first-child').clone()
        .addClass("cloneditem-"+(i))
        .appendTo($(this));
    }
  });
}());
/*end slider*/