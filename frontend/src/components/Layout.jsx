import React from 'react'

export default function Layout({ children }) {
  return (
    <div className="app">
      <div className="header card">
        <h2>WorldGen - Dashboard</h2>
        <div style={{fontSize:'0.9rem', color:'#6b7280'}}>REST + WebSocket live</div>
      </div>
      {children}
    </div>
  )
}
