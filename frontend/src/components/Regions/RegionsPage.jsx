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
    subscribe('/topic/regions', data => {
      setRegions(data)
    })
    // 🔽 NUEVO: escuchar notificaciones de regiones
subscribe('/topic/regions.notifications', msg => {
  if (!msg) return
  if (typeof msg === 'string') {
    pushNotification({ title: 'Actualización de Región', body: msg })
  } else {
    pushNotification({
      title: msg.title ?? 'Actualización de Región',
      body: msg.body ?? JSON.stringify(msg)
    })
  }
})

  // eslint-disable-next-line
}, [])


  const create = async payload => {
    try {
      await regionsApi.create(payload)
      await load()
      pushNotification({ text: `Región "${payload.name}" creada.` })
    } catch (e) {
      console.error(e)
      alert('Error al crear la región.')
    }
  }

  const remove = async id => {
    await regionsApi.remove(id)
    await load()
  }

  return (
    <div>
      <RegionForm onCreate={create} />
      <RegionList regions={regions} onDelete={remove} />
    </div>
  )
}
