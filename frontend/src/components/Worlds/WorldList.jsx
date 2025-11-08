import React from 'react'

export default function WorldList({ worlds = [], onDelete }) {
  return (
    <div className="card">
      <h4>Mundos</h4>
      <div className="list">
        {worlds.map(w => (
          <div key={w.id} style={{display:'flex', justifyContent:'space-between', alignItems:'center'}}>
            <div>
              <strong>{w.name}</strong> <span style={{color:'#6b7280'}}>ticks: {w.ticks}</span>
            </div>
            <div className="row">
              <button className="button small" onClick={() => onDelete(w.id)}>Eliminar</button>
            </div>
          </div>
        ))}
        {worlds.length === 0 && <div className="notice">No hay mundos</div>}
      </div>
    </div>
  )
}
