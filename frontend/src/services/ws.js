import SockJS from 'sockjs-client'
import Stomp from 'stompjs'

let client = null

export function connect(onConnect) {
  if (client && client.connected) {
    onConnect(client)
    return
  }

  // 🔽 Forzamos la URL absoluta (para evitar errores de proxy)
  const sock = new SockJS('http://localhost:8080/ws')

  client = Stomp.over(sock)
  client.debug = () => {} // desactivar logs

  client.connect(
    {},
    () => {
      console.log('✅ WS conectado')
      onConnect(client)
    },
    (err) => {
      console.error('❌ WS connect error', err)
    }
  )
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
      body = msg.body // si no es JSON, usar texto directamente
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

