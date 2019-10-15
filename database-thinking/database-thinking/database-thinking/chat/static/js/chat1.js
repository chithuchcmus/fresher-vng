async function checkToken(token) {
  if (!token) {
    location.href = "/";
    return;
  }
  let res = await fetch("/api/me", {
    headers: {
      Authorization: "Bearer " + token
    }
  }).then(res => res.json());
  console.log(res)
  if (res.code == 'fail') {
    location.href = "/";
    return;
  }
  else {
    res = res.data
    socket.emit('register_userRoom',{id:res.id})
    socket.emit('online event',{id:res.id})
    sessionStorage.setItem("userId", res.id)
    sessionStorage.setItem("userEmail", res.email)
    sessionStorage.setItem("userName", res.username)
  }
}

function showChatList(chat) {
  console.log(chat)
  let chat_list = document.createElement("div");
  chat_list.className = "chat_list";

  let chat_people = document.createElement("div");
  chat_people.className = "chat_people";
  chat_people.innerHTML = `<div class="chat_img">
    <img
      src="https://ptetutorials.com/images/user-profile.png"
      alt="sunil"
    />
  </div>`;
  chat_list.appendChild(chat_people);

  let chat_ib = document.createElement("div");
  chat_ib.className = "chat_ib";
  chat_ib.innerHTML = `<h5>${chat[2]}</h5>`;
  chat_people.appendChild(chat_ib);
  chat_list.setAttribute("id", chat[0]);
  chat_list.setAttribute("id_other",chat[1])
  $(".inbox_chat").append(chat_list);
}


function showMsgList(msg) {

  if (msg[3] == sessionStorage.getItem("userId")) {
    let div_msg = document.createElement("div");
    div_msg.className = "outgoing_msg";
    div_msg.innerHTML = `<div class="sent_msg">
    <p>${msg[1]}</p>
    <span class="time_date">${msg[2]}</span>
  </div>
`;

    $(".msg_history").append(div_msg);
  } else {
    let div_msg = document.createElement("div");
    div_msg.className = "incoming_msg";
    let incoming_msg_img = document.createElement("div");
    incoming_msg_img.className = "incoming_msg_img";
    incoming_msg_img.innerHTML = `<img
    src="https://ptetutorials.com/images/user-profile.png"
    alt="sunil"
  />
`;
    div_msg.appendChild(incoming_msg_img);
    let received_msg = document.createElement("div");
    received_msg.className = "received_msg";
    received_msg.innerHTML = `<div class="received_withd_msg">
    <p>${msg[1]}</p>
    <span class="time_date">${msg[2]}</span>
  </div>
`;
    div_msg.appendChild(received_msg);

    $(".msg_history").append(div_msg);
  }
}

function getTimenow() {
  let today = new Date();
  let dd = String(today.getDate()).padStart(2, '0');
  let mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
  let yyyy = today.getFullYear();
  let hour = today.getHours();
  let minus  = today.getMinutes();
  today = mm + '-' + dd + '-' + yyyy +' ' + hour + ':' + minus;
  return today;
}
var socket = io.connect('http://' + document.domain + ':' + location.port);

$(async () => {
  //wait until set succes id user
  await checkToken(sessionStorage.getItem("token"))
  id_user = parseInt(sessionStorage.getItem("userId"))
  let chat_list = await getListRoom(id_user)
  console.log(chat_list)
  for (let i = 0; i < chat_list.length; i += 1) {
    showChatList(chat_list[i]);
  }
  $("div.chat_list").on("click", async event => {
    $("div.chat_list").removeClass("active_chat");
    event.currentTarget.className = "chat_list active_chat";
    $(".msg_history").html("");
    window.idConversation = event.currentTarget.getAttribute("id")
    window.idOther  = event.currentTarget.getAttribute("id_other")

    //send event notificate to chat
    socket.emit('register_room',{ 
      id_receive: window.idOther,
      id_sender: sessionStorage.getItem("userId"), 
      room: window.idConversation });

    let msg_list = await getMsgListAPI(event.currentTarget.getAttribute("id"));
    if(msg_list===null)
      return;
    for (let i = 0; i < msg_list.length; i += 1) {
      showMsgList(msg_list[i]);
    }
    
  });

  $( "#send" ).click(function() {
    let message = $("#message-context").val();
    let idConversation = window.idConversation;
    let time = getTimenow()
    socket.emit('chat event', {
      context: message,
      create_at: time,
      id_sender: sessionStorage.getItem("userId"),
      room: idConversation
    })
    $("#message-context").val("");
  });

  $('#message-context').keypress(function (e) {
    var key = e.which;
    if(key == 13)  // the enter key code
     {
       $('#send').trigger('click');
       return false;  
     }
   });   

  $( "#search-to-chat" ).click(async function() {
    let email_receive = $("#email-to-chat").val();
    let id_sender = sessionStorage.getItem("userId")
    let email_sender = sessionStorage.getItem("userEmail")
    let res =  await createConversation(id_sender,email_sender,email_receive)
    if(res.code == 'succes')
    {
      let listIDConversation =  $( ".chat_list");
      var checkExistConversation = -1;
      for (let i = 0; i < listIDConversation.length; i++) {
        if(listIDConversation[i].getAttribute("id") === res.id_ConverSation)
        {
          checkExistConversation = i;
          break;
        }
      }
      if(checkExistConversation >= 0)
      {
        $('.chat_list').get(checkExistConversation).click()
      }
      else
      {
        let infochat = [res.idConversation,res.id_rev,res.use_name_receive]
        showChatList(infochat)
        location.reload();
      }

    }
    else
    {
      alert("Người bạn tìm không tồn tại!")
    }
  });
});


socket.on('response', function (msg) {
  let message = msg.context
  let id_sender = msg.id_sender
  let time = msg.create_at
  var infomess = ["temp",message,time,id_sender]
  console.log("mess reponse: " + message)
  showMsgList(infomess)

})

socket.on('start chat', function (msg) {
  room = msg.room;
  socket.emit('register_userRoom', {id:room})
})