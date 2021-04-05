// Custom Script
// Developed by: Samson.Onna
// CopyRights : http://webthemez.com
var customScripts = {
    profile: function () {
     	 var portfolio = $('#portfolio');
		var items = $('.items', portfolio); 
		var filters = $('.filters li a', portfolio); 
		items.imagesLoaded(function() {
			items.isotope({
				itemSelector: '.item',
				layoutMode: 'fitRows',
				transitionDuration: '0.7s'
			});
		});
		filters.click(function(){
			var el = $(this);
			filters.removeClass('active');
			el.addClass('active');
			var selector = el.attr('data-filter');
			items.isotope({ filter: selector });
			return false;
		});            
    },
    fancybox: function () {
        // fancybox
        $(".fancybox").fancybox();
    },
    onePageNav: function () {

        $('#mainNav').onePageNav({
            currentClass: 'active',
            changeHash: false,
            scrollSpeed: 950,
            scrollThreshold: 0.2,
            filter: '',
            easing: 'swing',
            begin: function () {
                //I get fired when the animation is starting
            },
            end: function () {
                   //I get fired when the animation is ending
				if(!$('#main-nav ul li:first-child').hasClass('active')){
					$('.header').addClass('addBg');
				}else{
						$('.header').removeClass('addBg');
				}
				
            },
            scrollChange: function ($currentListItem) {
                //I get fired when you enter a section and I pass the list item of the section
				if(!$('#main-nav ul li:first-child').hasClass('active')){
					$('.header').addClass('addBg');
				}else{
						$('.header').removeClass('addBg');
				}
			}
        });
		
		$("a[href='#top']").click(function () {
                $("html, body").animate({ scrollTop: 0 }, "slow");
                return false;
            });
			$("a[href='#basics']").click(function () {
                $("html, body").animate({ scrollTop: $('#services').offset().top - 75 }, "slow"); 
                return false;
            });
    }, 
    owlSlider: function () {
        var owl = $("#owl-demo");
        owl.owlCarousel();
        // Custom Navigation Events
        $(".next").click(function () {
            owl.trigger('owl.next');
        })
        $(".prev").click(function () {
            owl.trigger('owl.prev');
        })
    },
    bannerHeight: function () {
        var bHeight = $(".banner-container").height();
        $('#da-slider').height(bHeight);
        $(window).resize(function () {
            var bHeight = $(".banner-container").height();
            $('#da-slider').height(bHeight);
        });
    },
	waySlide: function(){
		  	/* Waypoints Animations
		   ------------------------------------------------------ */		   			 
			 						 
		},
		fitText: function(){			  
				setTimeout(function() {			
				$('h1.responsive-headline').fitText(1.2, { minFontSize: '16px', maxFontSize: '30px' });			
				}, 100);
		},
    init: function () {
        customScripts.onePageNav();
        customScripts.profile();
        customScripts.fancybox(); 
        customScripts.owlSlider();
		customScripts.waySlide();
		customScripts.fitText();
        customScripts.bannerHeight();
    }
}
$('document').ready(function () {
    customScripts.init(); 
	$('.carousel').carousel();
});
/*~~~~~~~~~~~~~~start fliper~~~~~~~~~~~~~~~~*/
 $('.fliper-btn').click(function(){
    $('.flip').find('.card').toggleClass('flipped');

});

  
/*~~~~~~~~~~~~ end fliper~~~~~~~~~~~~~*/

/*~~~~~~~~~~~~~~start tooltip~~~~~~~~~~~~*/

$(function(){
$('a[title]').tooltip();
});
/*~~~~~~~~~~~~~~~ end tooltip~~~~~~~~~~~~~~~*/

$(document).ready(function(){
    /*======= first fixed===*/
/*$(".close").click(function(){$("#fix1").fadeOut()});*/
/*======= end fixed===*/
     $("#ch").click(function(){
        $("#my-slider").toggle();
         $(".r-1").toggle();
        $("#num").toggle();
     });
     $("#col").click(function(){
        $(".fa").toggleClass('fa-plus-circle fa-minus-circle');       
    });
       $("#col1").click(function(){
        $(".fa").toggleClass('fa-plus-circle fa-minus-circle');       
    });
     /*~~~~~~~~~~~~~onclick more details~~~~~~~~~~*/
       /* $("#fix1").hide();
        $("#fix2").hide();
         var j=0;
      $("#fixed_t"+j).click(function(){
       $("#fix1").show();
       $("#fix2").hide();
       j++;
   });
       var k=1;
      $("#fixed_t"+k).click(function(){
        $("#fix2").show(); $("#fix1").hide();
        k++;
        console(k+"sadas");
      });*/
   /*  $("#fix1").hide();
        $("#fix2").hide();
     $('#fixed_t').click(function(i) {
    var index = $(this).index('a') + 1;
    $('#fix' + index).show().siblings('div').hide();
});*/
$(".close").click(function(){$(".fixed-1").removeClass("active");});

$("#read").click(function(){
$("#show").show();
$("#read").hide();
});
  /*~~~~~~~~~~ start show hide ~~~~~~~~*/
$("#ch1").click(function(){
$("#my-slider1").toggle();
$(".r-01").toggle();
$("#num1").toggle();
});
  /*~~~~~~~~~~ end show hide ~~~~~~~~*/
/*~~~end~~~~~*/
$('.panel-group .j1').click(function(){
    $('.j1 .fa').toggleClass('fa-angle-right fa-angle-down');
              });
  $('.panel-group .j2').click(function(){
    $('.j2 .fa').toggleClass('fa-angle-right fa-angle-down');
              });
    });
/*~~~~~~~~~~~~end rang slider~~~~~~*/
/*~~~~~~~~~~~~~~~end click more details~~~~~~~~~*/ 
/*onclick change icon*/
/*~~~~~end animation*/
/*~~~~start preloader~~~~~~*/
      $(window).on('load', function() { // makes sure the whole site is loaded 
  $('#prop').fadeOut(); // will first fade out the loading animation 
  $('#preloader').delay(350).fadeOut('slow'); // will fade out the white DIV that covers the website. 
  $('body').delay(350).css({'overflow':'visible'});
})
/*~~~~end preloader~~~~~~*/
 /* ~~~~~~~~start animation ~~~~*/
AOS.init({easing: 'ease-in-out-sine'});/*~~~~~~end animation~~~~*/


    
 
