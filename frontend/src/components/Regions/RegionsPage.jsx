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
      console.error('❌ Error cargando regiones', e)
    }
  }

  useEffect(() => {
    load()

    connect((client) => {
      console.log('✅ WS conectado (RegionsPage)')

      // Actualizaciones de regiones
      subscribe('/topic/regions', (data) => {
        console.log('📦 WS recibió /topic/regions:', data)
        if (Array.isArray(data)) {
          setRegions(data)
        }
      })

      // 🔔 Notificaciones
      subscribe('/topic/regions.notifications', (msg) => {
        console.log('📨 WS recibió /topic/regions.notifications:', msg)

        if (!msg) return

        if (typeof msg === 'string') {
          pushNotification({
            title: 'Actualización de Región',
            body: msg,
          })
        } else if (typeof msg === 'object') {
          pushNotification({
            title: msg.title ?? 'Actualización de Región',
            body: msg.body ?? JSON.stringify(msg),
          })
        } else {
          pushNotification({
            title: 'Actualización de Región',
            body: String(msg),
          })
        }
      })
    })

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  const create = async (payload) => {
    try {
      await regionsApi.create(payload)
      await load()
      pushNotification({
        title: 'Región creada (HTTP)',
        body: `Región "${payload.name}" creada exitosamente.`,
      })
    } catch (e) {
      console.error('❌ Error creando región', e)
      alert('Error al crear la región.')
    }
  }

  const remove = async (id) => {
    try {
      await regionsApi.remove(id)
      await load()
      pushNotification({
        title: 'Región eliminada (HTTP)',
        body: `Se eliminó la región con ID ${id}.`,
      })
    } catch (e) {
      console.error('❌ Error eliminando región', e)
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
