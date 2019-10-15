async function checkToken(token) {
  if (!token) {
    location.href = "/";
    return;
  }
  let res = await fetch("/api/me", {
    headers: {
      Authorization: "Bearer " + token
    }
  });
  res = await res.json();
  console.log(res)
  if (Object.keys(res).length == 1) {
    sessionStorage.removeItem("token")
    location.href = "/"
    return;
  }
  else {
    socket.emit('register_userRoom',{id:res.id})
    sessionStorage.setItem("userId", res.id)
    sessionStorage.setItem("userEmail", res.email)
    sessionStorage.setItem("userName", res.username)
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
var room = -1;
function scrollTop() {
  let chat = $("#chat");
  chat.scrollTop(chat.prop("scrollHeight"));
}
$(document).ready(async function () {
  let token = sessionStorage.getItem("token");
  checkToken(token);

})
$("#btnSearchFriend").on("click", async function () {
  let MailUser = $("#ChooseSpeaker").val();
  if (MailUser === "")
    return;
  let res = await fetch("/api/conversation", {
    method: "POST",
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      emailReceive: MailUser,
      emailSender: sessionStorage.getItem("userEmail"),
      idSender: sessionStorage.getItem("userId")
    })
  }).then(res => res.json())
  console.log(res)
  if (res.code == "succes") {
    room = res.id_ConverSation;
    idReceive = res.id_rev;
    socket.emit('register_room',{ id_receive: idReceive, id_sender: sessionStorage.getItem("userId"), room: room });
    showMessageLeft(
      "Hệ thống",
      "Tạo Phòng thành công",
      window.elementaryColor["Silver"][100],
      window.elementaryColor["Orange"][500],
    );
    scrollTop();
  }
});


$("#formChat").on("submit", event => {
  event.preventDefault();
  let inputMessage = $("#inputMessage");
  if (inputMessage.val() === "") {
    return;
  }
  socket.emit('chat event', {
    context: inputMessage.val(),
    create_at: getTimenow(),
    id_sender: sessionStorage.getItem("userId"),
    room: room
  })

  inputMessage.val("");
});



socket.on('response', function (msg) {
  message = msg.context
  id_sender = msg.id_sender
  if(id_sender == sessionStorage.getItem('userId'))
  {
    showMessageRight(
      message,
      window.elementaryColor["Silver"][100],
      window.elementaryColor["Blueberry"][500]
    )
  }
  else
  {
    showMessageLeft(
      id_sender,
      message,
      window.elementaryColor["Black"][700],
      window.elementaryColor["Silver"][300]
    );
  }
})

socket.on('start chat', function (msg) {
  room = msg.room;
  socket.emit('register_userRoom', {id:room})
})
function showMessageLeft(name, body, color, backgroundColor) {
  let row = document.createElement("div");
  row.className = "row my-1";

  let div_name = document.createElement("div");
  div_name.className = "col-sm-2 d-flex justify-content-start";
  row.appendChild(div_name);

  let span_name = document.createElement("span");
  span_name.className = "font-weight-bold p-2";
  span_name.innerText = name;
  div_name.appendChild(span_name);

  let div_body = document.createElement("div");
  div_body.className = "col-sm-8 d-flex justify-content-start";
  row.appendChild(div_body);

  let span_body = document.createElement("span");
  span_body.className = "rounded p-2 text-break";
  span_body.style.color = color;
  span_body.style.backgroundColor = backgroundColor;
  span_body.innerText = body;
  div_body.appendChild(span_body);

  $("#chat").append(row);
}

function showMessageRight(body, color, backgroundColor) {
  let row = document.createElement("div");
  row.className = "row my-1";

  let div_empty = document.createElement("div");
  div_empty.className = "col-sm-4";
  row.appendChild(div_empty);

  let div_body = document.createElement("div");
  div_body.className = "col-sm-8 d-flex justify-content-end";
  row.appendChild(div_body);

  let span_body = document.createElement("span");
  span_body.className = "rounded p-2 text-break";
  span_body.style.color = color;
  span_body.style.backgroundColor = backgroundColor;
  span_body.innerText = body;
  div_body.appendChild(span_body);

  $("#chat").append(row);
}