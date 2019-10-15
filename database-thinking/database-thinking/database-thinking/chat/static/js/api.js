async function getMsgListAPI(id_conversations) {
  var url = new URL("http://127.0.0.1:5000/api/messages"),
  params = {id_conversation:id_conversations}
  Object.keys(params).forEach(key => url.searchParams.append(key, params[key]))  
  let res = await fetch(url, {
    method: "GET",
    headers: { 'Content-Type': 'application/json' },
  }).then(res => res.json())
  console.log(res)
  if(res.code =='succes')
    return res.data
  return []
}

async function getListRoom(id_users) {
  var url = new URL("http://127.0.0.1:5000/api/conversations"),
  params = {id_user:id_users}
  Object.keys(params).forEach(key => url.searchParams.append(key, params[key]))  
  let res = await fetch(url, {
    method: "GET",
    headers: { 'Content-Type': 'application/json' },
  }).then(res => res.json())
  if(res.code =='succes')
    return res.data
  return []
}

async function getUserSameRoom(id_conversations) {
  var url = new URL("http://127.0.0.1:5000/api/participants"),
  params = {id_conversation:id_conversations}
  Object.keys(params).forEach(key => url.searchParams.append(key, params[key]))  
  let res = await fetch(url, {
    method: "GET",
    headers: { 'Content-Type': 'application/json' },
  }).then(res => res.json())
  if(res.code =='succes')
    return res.data
  return []
}

async function createConversation(id_sender,email_sender,email_receive) {
  let res = await fetch("/api/conversation", {
    method: "POST",
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      emailReceive: email_receive,
      emailSender: email_sender,
      idSender: id_sender
    })
  }).then(res => res.json())
  console.log(res)
  return res
}

async function makeFriend(id_sender,email_receive) {
  let res = await fetch("/api/friends", {
    method: "POST",
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      emailReceive: email_receive,
      idSender: id_sender
    })
  }).then(res => res.json())
  return res
}

async function getFriend(id_users) {
  var url = new URL("http://127.0.0.1:5000/api/friends"),
  params = {id_user:id_users}
  Object.keys(params).forEach(key => url.searchParams.append(key, params[key]))  
  let res = await fetch(url, {
    method: "GET",
    headers: { 'Content-Type': 'application/json' },
  }).then(res => res.json())
  console.log(res)
  if(res.code =='succes')
    return res.data
  return []
}
