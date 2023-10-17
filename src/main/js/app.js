
const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {employees: []};
    }

    componentDidMount() {
        // client({method: 'GET', path: '/api/employees'}).done(response => {
        //     this.setState({employees: response.entity._embedded.employees});
        // });
    }

    render() {

        return (
            // <div className="header">
            //     <div className="headerLayout">
            //         <div className="headerTitle">
            //             <h1>QA Platform</h1>
            //         </div>
            //         <div className="headerSearch">
            //             <h3>Fulltext search will be here</h3>
            //         </div>
            //     </div>
            //     <div className="headerButtons">
            //         There will be buttons here: <b>[Project selector] [Test cases] [Environments] [Test templates]</b>
            //     </div>
            // </div>
        // <hr/>
        <div className="content">
            <div className="contentLayout">
                <div className="testCases">
                    <h3>Test cases list</h3>
                    testReact
                </div>
                <div className="testCaseDetails">
                    <h3>Test case details</h3>
                </div>
            </div>
        </div>
        // <hr/>
        // <div className="footer">
        //     Footer
        // </div>
        )
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('react')
)