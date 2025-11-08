import React, { useEffect, useState } from 'react'
import { regionsApi } from '../../services/api'
import RegionForm from './RegionForm'
import RegionList from './RegionList'
import { connect, subscribe } from '../../services/ws'

export default function RegionsPage({ pushNotification }) {
  const [regions, setRegions] = useState([])

  const load = async () => {
    const r = await regionsApi.list()
    setRegions(r)
  }

  useEffect(() => {
    load()
    connect(client => {
      subscribe('/topic/regions', data => {
        setRegions(data)
      })
      // additionally subscribe to region events if desired
    })
    // eslint-disable-next-line
  }, [])

  const create = async (payload) => {
    await regionsApi.create(payload)
    await load()
  }

  const remove = async (id) => {
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
