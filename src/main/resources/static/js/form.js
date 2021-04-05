$(document).ready(function () {
    $('#signupForm').validate({ // initialize the plugin
      rules: {
          name : {
            required: true,
            letterswithspace: true,
            minlength: 4,
            maxlength: 40
          },
          email: {
            required: true,
            email: true,
            maxlength: 40
          },
          contact: {
            required: true,
            digits: true,
            minlength: 10,
            maxlength: 10
          },
          password : {
            required: true,
            minlength: 5,
            maxlength: 20
          },
          username : {
            required: false,
            lettersonly: true,
            //minlength: 4,
            maxlength: 15,
          },

          city: {
            required: false,
            lettersonly: true,
            //minlength: 4,
            maxlength: 25
          },
          address: {
            required: false,
            //minlength: 4,
            maxlength: 250
          }
        }
    });

    $('#loginForm').validate({ // initialize the plugin
      rules: {
          usernameOrEmail : {
            required: true
          },
          password : {
            required: true
          }
        }
    });

    $('#pwdForm').validate({ // initialize the plugin
          rules: {
              currentPass : {
                required: true
              },
              newPass : {
                required: true,
                minlength: 5,
                maxlength: 20
              },
             confirmPass : {
               required: true,
               equalTo : "#newPass"
             }
            }
        });

    $('#profileForm').validate({ // initialize the plugin
          rules: {
              name : {
                required: true,
                letterswithspace: true,
                minlength: 4,
                maxlength: 40
              },
              email: {
                required: true,
                email: true,
                maxlength: 40
              },
              contact: {
                required: true,
                digits: true,
                minlength: 10,
                maxlength: 10
              },
              username : {
                required: false,
                lettersonly: true,
                //minlength: 4,
                maxlength: 15,
              },

              city: {
                required: false,
                lettersonly: true,
                //minlength: 4,
                maxlength: 25
              },
              address: {
                required: false,
                //minlength: 4,
                maxlength: 250
              }
            }
        });

});


$("#signupForm").submit(function (e) {
    e.preventDefault();
    if($('#signupForm').valid()) {
        $("body").removeClass("loading").addClass("loading");
        var signupForm = {}
        signupForm["name"] = $("#name").val();
        signupForm["email"] = $("#email").val();
        signupForm["contact"] = $("#contact").val();
        signupForm["username"] = $("#username").val();
        signupForm["password"] = $("#password").val();
        signupForm["city"] = $("#city").val();
        signupForm["address"] = $("#address").val();
        $.ajax({
            type: "POST",
            url: "/api/auth/signup",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(signupForm),
            //async: false,
            success: function(data) {
               console.log('data', data);
               if (data != null && data != undefined) {
                $('.alert-success').fadeIn().text("User registration successful.").delay(5000).fadeOut();
                $("#signupForm").hide();

               }
               $("body").removeClass("loading");
            },
            error: function (e) {
                console.log('error', e);
                $("body").removeClass("loading");
                if(e.responseJSON != null) {
                    $('.alert-danger').fadeIn().text(e.responseJSON.message).delay(5000).fadeOut();
                } else {
                    $('.alert-danger').fadeIn().text('Oops... something went wrong. Please try after sometime.').delay(5000).fadeOut();
                }
            }
        });
    }
});

/*$("#profileForm").submit(function (e) {
    e.preventDefault();
    if($('#profileForm').valid()) {
        $("body").removeClass("loading").addClass("loading");
        var profileForm = {}
        profileForm["name"] = $("#name").val();
        profileForm["email"] = $("#email").val();
        profileForm["contact"] = $("#contact").val();
        profileForm["username"] = $("#username").val();
        profileForm["id"] = $("#id").val();
        profileForm["city"] = $("#city").val();
        profileForm["address"] = $("#address").val();
        $.ajax({
            type: "POST",
            url: "/api/user/profile",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(profileForm),
            //async: false,
            success: function(data) {
               console.log('data', data);
               if (data != null && data != undefined) {
                $('.alert-success').fadeIn().text("Profile updated successful.").delay(5000).fadeOut();
               }
               $("body").removeClass("loading");
            },
            error: function (e) {
                console.log('error', e);
                $("body").removeClass("loading");
                if(e.responseJSON != null) {
                    $('.alert-danger').fadeIn().text(e.responseJSON.message).delay(5000).fadeOut();
                } else {
                    $('.alert-danger').fadeIn().text('Oops... something went wrong. Please try after sometime.').delay(5000).fadeOut();
                }
            }
        });
    }
});*/

$("#loginForm").submit(function (e) {
    e.preventDefault();
    if($('#loginForm').valid()) {
        $("body").addClass("loading");
        var loginForm = {}
        loginForm["usernameOrEmail"] = $("#usernameOrEmail").val();
        loginForm["password"] = $("#password").val();
        $.ajax({
            type: "POST",
            url: "/api/auth/signin",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(loginForm),
            //async: false,
            success: function(data) {
               //console.log(data);
               if (data != null && data != undefined) {
                    var expiresAt = moment(Number(data.expiresIn)).utc();
                    window.localStorage.setItem('id_token', data.accessToken);
                    window.localStorage.setItem('type_token', data.tokenType);
                    window.localStorage.setItem('has_role', data.hasRole);
                    window.localStorage.setItem("expires_at", JSON.stringify(expiresAt.valueOf()) );
                    window.location.href = '/';
               }
               $("body").removeClass("loading");
            },
            error: function (e) {
                $("body").removeClass("loading");
                $('.alert-danger').fadeIn().text('Invalid username or password').delay(5000).fadeOut();
            }
        });
    }
});