import SockJS from 'sockjs-client'
import Stomp from 'stompjs'

let client = null

export function connect(onConnect) {
  if (client && client.connected) {
    onConnect(client)
    return
  }

  const sock = new SockJS('/ws') // endpoint que registraste en backend
  client = Stomp.over(sock)
  client.debug = () => {} // desactivar logs

  client.connect({}, () => {
    onConnect(client)
  }, (err) => {
    console.error('WS connect error', err)
  })
}

export function subscribe(destination, handler) {
  if (!client) {
    console.warn('WS client not connected')
    return null
  }
  return client.subscribe(destination, msg => {
    let body
    try {
      body = JSON.parse(msg.body)
    } catch {
      body = msg.body // si no es JSON, usar el texto directamente
    }
    handler(body)
  })
}


export function disconnect() {
  if (client) {
    client.disconnect()
    client = null
  }
}
