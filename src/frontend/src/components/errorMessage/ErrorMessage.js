import img from './error.gif'

const ErrorMessage = () => {
    return (
        <img alt="errorImg" style={{margin: '0 auto', background: 'none', display: 'block', width: "250px", height:"250px", objectFit:'contain'}} src={img} />
    )
}

export default ErrorMessage;