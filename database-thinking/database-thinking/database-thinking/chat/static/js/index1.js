$(() => {
    sessionStorage.clear();
  
    // only show log in
    $("#cardSignUp").addClass("d-none");
    $("#cardLogIn").removeClass("d-none");
  
    $("#cardSignUp a").on("click", () => {
      $("#cardSignUp").addClass("d-none");
      $("#cardLogIn").removeClass("d-none");
    });
  
    $("#cardLogIn a").on("click", () => {
      $("#cardSignUp").removeClass("d-none");
      $("#cardLogIn").addClass("d-none");
    });

    $("#cardSignUp form").on("submit", async event => {
      event.preventDefault();
      let username = document.getElementById("inputRegisterNameSignUp").value;
      let password = document.getElementById("inputPasswordSignUp").value;
      let gmail = document.getElementById("inputFullNameSignUp").value;

      let res = await fetch("/api/auth/register", {
          method: "POST",
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
              username: username,
              password: password,
              email: gmail
          })
      }).then(res => res.json())
      console.log(res)
      if(res.code=="success")
      {
          alert("Đăng ký thành công")
      }
      else
      {
          alert("Đăng ký thất bại")
      }
      location.href = "/";

    });
  
    $("#cardLogIn form").on("submit", async event => {
        event.preventDefault();

        let regname = document.getElementById("inputRegisterNameLogIn").value;
        let password = document.getElementById("inputPasswordLogIn").value;

        let res = await fetch("/api/auth/login", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                email: regname,
                password: password
            })
        }).then(res => res.json())

        if(res.code == "succes")
        {
            console.log(res.code)
            sessionStorage.setItem("token",res.token);
            window.location.href="/chat"
        }
        else{
            alert("Email hoặc mật khẩu không chính xác")
        }
        //sessionStorage.setItem("token", res.token);
    });
  });


  
