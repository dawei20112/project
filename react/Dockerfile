FROM node:19-alpine
WORKDIR /app
COPY package*.json .
RUN npm i --silent
COPY . .
#后端接口信息
RUN echo "VITE_API_BASE_URL=http://localhost:8090" > .env
EXPOSE 5173
CMD ["npm" ,"run","dev"]