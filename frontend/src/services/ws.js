import SockJS from 'sockjs-client'
import Stomp from 'stompjs'

let client = null
let connected = false
let connectCallbacks = []

export function connect(onConnect) {
  if (client && connected) {
    onConnect(client)
    return
  }

  const sock = new SockJS('http://localhost:8080/ws') // 👈 forzamos URL absoluta
  client = Stomp.over(sock)
  client.debug = () => {} // silenciar logs internos

  client.connect(
    {},
    () => {
      connected = true
      console.log('✅ WS conectado correctamente')
      connectCallbacks.forEach(cb => cb(client))
      connectCallbacks = []
      onConnect(client)
    },
    (err) => {
      connected = false
      console.error('❌ WS connect error', err)
      setTimeout(() => connect(onConnect), 5000) // 👈 reintento automático
    }
  )
}

export function subscribe(destination, handler) {
  if (!client || !connected) {
    console.warn('⚠️ WS no conectado aún, esperando...')
    connect(c => subscribe(destination, handler))
    return
  }

  return client.subscribe(destination, msg => {
    let body
    try {
      body = JSON.parse(msg.body)
    } catch {
      body = msg.body // texto plano
    }
    console.log(`📨 Mensaje recibido de ${destination}:`, body)
    handler(body)
  })
}

export function disconnect() {
  if (client && connected) {
    client.disconnect(() => {
      console.log('🔌 WS desconectado')
      client = null
      connected = false
    })
  }
}


