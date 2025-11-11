import React, { useEffect, useState } from 'react'
import { regionsApi } from '../../services/api'
import RegionForm from './RegionForm'
import RegionList from './RegionList'
import { connect, subscribe } from '../../services/ws'

export default function RegionsPage({ pushNotification }) {
  const [regions, setRegions] = useState([])

  const load = async () => {
    try {
      const r = await regionsApi.list()
      setRegions(r)
    } catch (e) {
      console.error('Error cargando regiones', e)
    }
  }

  useEffect(() => {
    load()

    connect(client => {
      // 🔹 Actualización de la lista de regiones en tiempo real (si backend lo emite)
      subscribe('/topic/regions', data => {
        if (Array.isArray(data)) setRegions(data)
      })

      // 🔹 Escuchar notificaciones emitidas desde el backend
      subscribe('/topic/regions.notifications', msg => {
        if (!msg) return

        if (typeof msg === 'string') {
          pushNotification({
            title: 'Actualización de Región',
            body: msg
          })
        } else {
          pushNotification({
            title: msg.title ?? 'Actualización de Región',
            body: msg.body ?? JSON.stringify(msg)
          })
        }
      })
    })

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  const create = async payload => {
    try {
      await regionsApi.create(payload)
      await load()
      // 🔹 El backend ya envía una notificación por WebSocket, así que no repetimos
      // Si querés mantener el mensaje local, podés descomentar la siguiente línea:
      // pushNotification({ title: 'Región creada', body: `Región "${payload.name}" creada.` })
    } catch (e) {
      console.error(e)
      alert('Error al crear la región.')
    }
  }

  const remove = async id => {
    try {
      await regionsApi.remove(id)
      await load()
    } catch (e) {
      console.error(e)
      alert('Error al eliminar la región.')
    }
  }

  return (
    <div>
      <RegionForm onCreate={create} />
      <RegionList regions={regions} onDelete={remove} />
    </div>
  )
}
