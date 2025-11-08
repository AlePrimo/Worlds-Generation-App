import React, { useState, useEffect } from 'react'
import { worldsApi } from '../../services/api'

export default function RegionForm({ onCreate }) {
  const [name, setName] = useState('')
  const [lat, setLat] = useState(0)
  const [lon, setLon] = useState(0)
  const [population, setPopulation] = useState(0)
  const [water, setWater] = useState(0)
  const [food, setFood] = useState(0)
  const [minerals, setMinerals] = useState(0)
  const [alive, setAlive] = useState(true)
  const [worldName, setWorldName] = useState('')
  const [worlds, setWorlds] = useState([])

  useEffect(() => {
    worldsApi.list().then(setWorlds)
  }, [])

  const submit = async (e) => {
    e.preventDefault()
    const payload = { name, lat, lon, population, water, food, minerals, alive, worldName }
    await onCreate(payload)
    setName('')
  }

  return (
    <form className="card" onSubmit={submit}>
      <h4>Crear Región</h4>
      <div className="form-field"><label>Nombre</label><input value={name} onChange={e=>setName(e.target.value)} /></div>
      <div className="form-field"><label>Lat</label><input type="number" value={lat} onChange={e=>setLat(parseFloat(e.target.value))} /></div>
      <div className="form-field"><label>Lon</label><input type="number" value={lon} onChange={e=>setLon(parseFloat(e.target.value))} /></div>
      <div className="form-field"><label>Población</label><input type="number" value={population} onChange={e=>setPopulation(parseInt(e.target.value))} /></div>
      <div className="form-field"><label>Agúa</label><input type="number" value={water} onChange={e=>setWater(parseFloat(e.target.value))} /></div>
      <div className="form-field"><label>Alimento</label><input type="number" value={food} onChange={e=>setFood(parseFloat(e.target.value))} /></div>
      <div className="form-field"><label>Minerales</label><input type="number" value={minerals} onChange={e=>setMinerals(parseFloat(e.target.value))} /></div>
      <div className="form-field"><label>Mundo</label>
        <select value={worldName} onChange={e=>setWorldName(e.target.value)}>
          <option value="">-- seleccionar --</option>
          {worlds.map(w => <option key={w.id} value={w.name}>{w.name}</option>)}
        </select>
      </div>
      <div className="row">
        <button className="button small" type="submit">Crear Región</button>
      </div>
    </form>
  )
}
