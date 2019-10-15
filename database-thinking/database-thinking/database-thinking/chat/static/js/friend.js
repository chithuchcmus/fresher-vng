
$(async () => {
    let listFriend = await getFriend(sessionStorage.getItem("userId"))
    console.log(listFriend)
    for (let i = 0; i < listFriend.length; i += 1) {
        showFriendList(listFriend[i]);
      }

    $( "#make-friend" ).click(async function() {
        let emailReceive = $("#email-friend").val();
        if(emailReceive == "")
            return;
        id_sender = sessionStorage.getItem("userId");
        let res = await makeFriend(id_sender,emailReceive);
        if(res.code == 'succes')
        {
            alert("Kết bạn thành công")
            location.reload()
        }
        else
        {
            alert("Không tồn tại email người nhận")
        }
      });
});

function showFriendList(info) {
    let a = ` <li class="list-group-item">
    <div class="col-xs-12 col-sm-3">
        <img src="https://ptetutorials.com/images/user-profile.png" alt="Seth Frazier"
            class="img-responsive img-circle" />
    </div>
    <div class="col-xs-12 col-sm-9">
        <span class="name">Name: ${info[1]}</span><br />
        <span class="name">Email: ${info[2]}</span><br />
    </div>
    <div class="clearfix"></div>
    </li>`
    $(".list-group").append(a);
}