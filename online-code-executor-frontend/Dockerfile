FROM node:15.4.0-alpine3.10 as build

WORKDIR /app

COPY package.json ./
COPY package-lock.json ./

RUN npm install --silent

COPY . ./

RUN npm run build

ENTRYPOINT [ "npm", ]

FROM nginx:1.19.6 as frontend

COPY nginx/nginx.conf /etc/nginx/nginx.conf

COPY --from=build /app/build/ /var/www/html/