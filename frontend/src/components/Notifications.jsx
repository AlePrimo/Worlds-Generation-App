import React from 'react'

export default function Notifications({ messages = [] }) {
  return (
    <div className="card">
      <h4>Notificaciones</h4>
      {messages.length === 0 && <div className="notice">Sin notificaciones</div>}
      <div className="list">
        {messages.slice().reverse().map((m, idx) => (
          <div key={idx} className="event">
            <div><strong>{m.title}</strong></div>
            <div style={{fontSize: '0.9rem'}}>{m.body}</div>
            <div style={{fontSize:'0.8rem', color:'#6b7280'}}>{m.time}</div>
          </div>
        ))}
      </div>
    </div>
  )
}
