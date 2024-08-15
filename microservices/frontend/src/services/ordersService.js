import axios from 'axios';

const API_URL = 'http://k8s-default-appingre-ba32882f97-1969665003.us-east-1.elb.amazonaws.com/orders'

// Create an Axios instance with SSL verification disabled
const axiosInstance = axios.create({
  baseURL: API_URL,
  // Disable SSL verification (for development only)
  httpsAgent: new (require('https').Agent)({
    rejectUnauthorized: false,
  }),
});

export const getOrders = async () => {
  const response = await axiosInstance.get('');
  return response.data;
};

export const deleteOrder = async (id) => {
  await axiosInstance.delete(`/${id}`);
};

