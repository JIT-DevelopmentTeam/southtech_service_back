import moment from 'moment'

export function format(dateTime) {
	return moment(dateTime).format('YYYY-MM-DD HH:mm')
}


export function formatDate(dateTime,prop) {
	return moment(dateTime).format(prop)
}