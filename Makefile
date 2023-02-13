local-up:
	docker-compose -f docker-compose-local.yml up
prod-up:
	docker-compose up --build
