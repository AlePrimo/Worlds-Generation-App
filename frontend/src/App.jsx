import React, { useState } from 'react'
import Layout from './components/Layout'
import WorldsPage from './components/Worlds/WorldsPage'
import RegionsPage from './components/Regions/RegionsPage'
import Notifications from './components/Notifications'

export default function App() {
  const [tab, setTab] = useState('worlds')
  const [messages, setMessages] = useState([])

  const pushNotification = (msg) => {
    setMessages(prev => [...prev, {...msg, time: msg.time ?? new Date().toLocaleTimeString()}].slice(-20))
  }

  return (
    <Layout>
      <div style={{display:'flex', gap:12}}>
        <div style={{flex:1}}>
          <div style={{display:'flex', gap:8, marginBottom:12}}>
            <button className="button small" onClick={() => setTab('worlds')}>Mundos</button>
            <button className="button small" onClick={() => setTab('regions')}>Regiones</button>
          </div>

          {tab === 'worlds' && <WorldsPage pushNotification={pushNotification} />}
          {tab === 'regions' && <RegionsPage pushNotification={pushNotification} />}
        </div>

        <div style={{width:340}}>
          <Notifications messages={messages} />
        </div>
      </div>
    </Layout>
  )
}
