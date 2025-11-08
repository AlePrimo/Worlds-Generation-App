import React, { useEffect, useState } from 'react'
import { worldsApi } from '../../services/api'
import WorldForm from './WorldForm'
import WorldList from './WorldList'
import { connect, subscribe } from '../../services/ws'

export default function WorldsPage({ pushNotification }) {
  const [worlds, setWorlds] = useState([])

  const load = async () => {
    const w = await worldsApi.list()
    setWorlds(w)
  }

  useEffect(() => {
    load()
    connect(client => {
      subscribe('/topic/worlds', data => {
        setWorlds(data)
        pushNotification({ title: 'Lista de mundos actualizada', body: 'Lista completa recibida', time: new Date().toLocaleTimeString() })
      })
      // subscribe ticks and single-world updates (wildcard not supported; subscribe per world only when needed)
    })
    // eslint-disable-next-line
  }, [])

  const create = async (payload) => {
    await worldsApi.create(payload)
    await load()
  }

  const remove = async (id) => {
    await worldsApi.remove(id)
    await load()
  }

  return (
    <div>
      <WorldForm onCreate={create} />
      <WorldList worlds={worlds} onDelete={remove} />
    </div>
  )
}
