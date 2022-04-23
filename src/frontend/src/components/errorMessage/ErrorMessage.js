import img from './error.gif'
import "./errorMessage.scss"

const ErrorMessage = () => {
    return (
        <div className="errorMessage">
            <img alt="errorImg" style={{margin: '0 auto', background: 'none', display: 'block', width: "300px", height:"300px", objectFit:'contain'}} src={img} />
        </div>
    )
}

export default ErrorMessage;