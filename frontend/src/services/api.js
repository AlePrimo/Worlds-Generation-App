import axios from 'axios'

const api = axios.create({
  baseURL: '/api', // proxyarlo en dev o configurar CORS en backend
  timeout: 5000
})

export const worldsApi = {
  list: () => api.get('/worlds').then(r => r.data),
  get: (id) => api.get(`/worlds/${id}`).then(r => r.data),
  create: (payload) => api.post('/worlds', payload).then(r => r.data),
  update: (id, payload) => api.put(`/worlds/${id}`, payload).then(r => r.data),
  remove: (id) => api.delete(`/worlds/${id}`).then(r => r.data)
}

export const regionsApi = {
  list: () => api.get('/regions').then(r => r.data),
  get: (id) => api.get(`/regions/${id}`).then(r => r.data),
  create: (payload) => api.post('/regions', payload).then(r => r.data),
  update: (id, payload) => api.put(`/regions/${id}`, payload).then(r => r.data),
  remove: (id) => api.delete(`/regions/${id}`).then(r => r.data)
}

export const eventsApi = {
  list: () => api.get('/events').then(r => r.data)
}
